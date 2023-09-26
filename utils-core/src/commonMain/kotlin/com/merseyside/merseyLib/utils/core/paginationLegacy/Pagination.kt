package com.merseyside.merseyLib.utils.core.paginationLegacy

import com.merseyside.merseyLib.kotlin.entity.result.Result
import com.merseyside.merseyLib.kotlin.entity.result.filterSuccessValues
import com.merseyside.merseyLib.kotlin.logger.ILogger
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.squareup.sqldelight.internal.AtomicBoolean
import kotlinx.coroutines.flow.*

@Deprecated("Use new pagination")
abstract class Pagination<PD, Data, Page>(
    private val initPrevPage: Page,
    private val initNextPage: Page
) : ILogger
        where PD : PagerData<Data, Page> {

    private var lastData: PD? = null

    var currentPrevPage: Page = initPrevPage
    var currentNextPage: Page = initNextPage

    private val pages = mutableListOf(Pair<Page?, Page?>(initPrevPage, initNextPage))

    private val mutSharedFlow: MutableSharedFlow<Result<Data>> =
        MutableSharedFlow(extraBufferCapacity = 10)
    val resultFlow: Flow<Result<Data>> = mutSharedFlow

    //val dataFlow: Flow<Data> = resultFlow.filterSuccessValues()

    private fun getNextPage(): Page {
        return safeLet(lastData) {
            pages.lastOrNull()?.second
        } ?: initNextPage
    }

    private fun getPrevPage(): Page {
        return safeLet(lastData) {
            pages.firstOrNull()?.first
        } ?: initPrevPage
    }

    abstract suspend fun loadData(prevPage: Page, nextPage: Page): PD

    private suspend fun onDataLoaded(pagerData: PD) {
        lastData = pagerData
        emitResult(Result.Success(pagerData.data))
    }

    private fun isNextPageValid(): Boolean {
        return getNextPage() != null || lastData == null
    }

    private fun isPrevPageValid(): Boolean {
        return getPrevPage() != null
    }

    fun resetPaging() {
        lastData = null
        currentNextPage = initNextPage
        currentPrevPage = initPrevPage
        pages.clear()
    }


    suspend fun loadNextPage() {
        loadPage(::isNextPageValid) {
            currentNextPage = getNextPage()
            loadData(initPrevPage, currentNextPage).also {
                pages.add(it.prevPage to it.nextPage)
            }
        }
    }

    suspend fun loadPrevPage() {
        loadPage(::isPrevPageValid) {
            currentPrevPage = getPrevPage()
            loadData(currentPrevPage, initNextPage).also {
                pages.add(0, it.prevPage to it.nextPage)
            }
        }
    }

    private var isLoading = AtomicBoolean(false)

    private suspend fun loadPage(
        isPageValid: () -> Boolean,
        loadWork: suspend () -> PD
    ) {
        if (isLoading.get()) return
        if (!isPageValid()) {
            logMsg("No page")
            return
        }
        try {
            isLoading.set(true)
            emitResult(Result.Loading())
            onDataLoaded(loadWork())
        } catch (e: Exception) {
            emitResult(Result.Error(e))
        } finally {
            isLoading.set(false)
        }
    }


    private suspend fun emitResult(result: Result<Data>) {
        mutSharedFlow.emit(result)
    }

    fun isLoadedData(): Boolean {
        return lastData != null
    }

    override val tag: String = "Pagination"
}