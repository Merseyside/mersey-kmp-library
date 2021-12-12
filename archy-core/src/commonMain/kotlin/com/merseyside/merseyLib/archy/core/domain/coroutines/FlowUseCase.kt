package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.kotlin.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class FlowUseCase<T, Params> : CoroutineScope by CoroutineScope(applicationContext) {
    var job: Job? = null
    var backgroundContext: CoroutineContext = computationContext

    @ExperimentalCoroutinesApi
    protected abstract fun executeOnBackground(params: Params?): Flow<T>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observe(
        coroutineScope: CoroutineScope = this,
        params: Params? = null,
        onEmit: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ): Job {
        val flow = executeOnBackground(params)
            .flowOn(backgroundContext)

        job?.let {
            cancel()
        }

        return coroutineScope.launch {
            try {
                flow.collect { data ->
                    onEmit.invoke(data)
                }
            } catch (e: CancellationException) {
                Logger.log(this@FlowUseCase, "Coroutine had canceled")
            }
            catch (e: Throwable) {
                Logger.log(e)
                onError.invoke(e)
            }
        }.also { job = it }
    }

    fun cancel() {
        job?.cancel()
        job = null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(params: Params? = null): Flow<T> {
        return executeOnBackground(params)
    }
}