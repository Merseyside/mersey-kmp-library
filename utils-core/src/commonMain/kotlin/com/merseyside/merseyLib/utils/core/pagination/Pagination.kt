package com.merseyside.merseyLib.utils.core.pagination

import com.merseyside.merseyLib.kotlin.entity.Result
import com.merseyside.merseyLib.kotlin.logger.ILogger
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.kotlin.utils.safeLet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

abstract class Pagination<PD, Data, Page>(
    private val initNextPage: Page,
    private val initPrevPage: Page
) : ILogger
        where PD : PagerData<Data, Page> {

    private var lastData: PD? = null
        set(value) {
            field = value
            safeLet(value) { data ->
                if (data.prevPage != null && data.nextPage != null) {
                    if (!newPages.any { it.values.contains(data.prevPage) }) {
                        newPages.add(0, mapOf(data.prevPage to data.nextPage))
                    }
                }
                newPages.add(mapOf(data.prevPage to data.nextPage))
            }
        }
    var currentNextPage: Page = initNextPage
    var currentPrevPage: Page = initPrevPage

    private val newPages = mutableListOf(mapOf<Page?, Page?>(initPrevPage to initNextPage))

    private val pages = mutableMapOf<Page?, Page?>(initPrevPage to initNextPage)

    private val mutSharedFlow: MutableSharedFlow<Result<Data>> =
        MutableSharedFlow(extraBufferCapacity = 10)
    val resultFlow: Flow<Result<Data>> = mutSharedFlow

    val dataFlow: Flow<Data> = resultFlow.filterIsInstance<Result.Success<Data>>().map { it.value }

    private fun getNextPage(): Page {
        return safeLet(lastData) {
            newPages.lastOrNull()?.values?.lastOrNull()
        } ?: initNextPage
    }

    private fun getPrevPage(): Page {
        return safeLet(lastData) {
            newPages.firstOrNull()?.keys?.firstOrNull()
        } ?: initPrevPage
    }

    abstract suspend fun loadData(nextPage: Page, prevPage: Page): PD

    private suspend fun onDataLoaded(pagerData: PD) {
        lastData = pagerData
        emitResult(Result.Success(pagerData.data))
    }

    private fun isNextPageValid(): Boolean {
        return getNextPage() != null || lastData == null
    }

    private fun isPrevPageValid(): Boolean {
        return getPrevPage() != null || lastData == null
    }

    fun resetPaging() {
        lastData = null
        currentNextPage = initNextPage
        currentPrevPage = initPrevPage
        newPages.clear()
    }

    suspend fun loadNextPage() {
        if (!isNextPageValid()) {
            logMsg("No next page")
            return
        }

        currentNextPage = getNextPage()

        try {
            val newData = loadData(currentNextPage, initPrevPage)
            emitResult(Result.Loading())
            onDataLoaded(newData)
        } catch (e: Exception) {
            emitResult(Result.Error(e))
        }
    }

    suspend fun loadPrevPage() {
        if (!isPrevPageValid()) {
            logMsg("No prev page")
            return
        }

        currentPrevPage = getPrevPage()

        try {
            val newData = loadData(initNextPage, currentPrevPage)
            emitResult(Result.Loading())
            onDataLoaded(newData)
        } catch (e: Exception) {
            emitResult(Result.Error(e))
        }
    }

    private suspend fun emitResult(result: Result<Data>) {
        mutSharedFlow.emit(result)
    }

    override val tag: String = "Pagination"
}