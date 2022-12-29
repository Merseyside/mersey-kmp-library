package com.merseyside.merseyLib.utils.core.pagination

import com.merseyside.merseyLib.kotlin.entity.Result
import com.merseyside.merseyLib.kotlin.logger.ILogger
import com.merseyside.merseyLib.kotlin.utils.safeLet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

abstract class Pagination<PD, Data, Page>(private val initPage: Page): ILogger
        where PD : PagerData<Data, Page> {

    private var lastData: PD? = null
    var currentPage: Page = initPage

    private val mutSharedFlow: MutableSharedFlow<Result<Data>> = MutableSharedFlow(extraBufferCapacity = 10)
    val resultFlow: Flow<Result<Data>> = mutSharedFlow

    val dataFlow: Flow<Data> = resultFlow.filterIsInstance<Result.Success<Data>>().map { it.value }

    private fun getNextPage(): Page {
        return safeLet(lastData) {
            it.nextPage
        } ?: initPage
    }

    abstract suspend fun loadData(page: Page): PD

    private suspend fun onDataLoaded(pagerData: PD) {
        lastData = pagerData
        emitResult(Result.Success(pagerData.data))
    }

    private fun isNextPageValid(): Boolean {
        return getNextPage() != null || lastData == null
    }

    suspend fun loadNextPage() {
        if (!isNextPageValid()) {
            logMsg("No next page")
            return
        }

        currentPage = getNextPage()

        try {
            val newData = loadData(currentPage)
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