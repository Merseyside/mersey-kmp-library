package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.utils.core.Logger
import kotlinx.coroutines.*

abstract class CoroutineUseCase<T, Params> : BaseCoroutineUseCase<T, Params>() {

    fun execute(
        coroutineScope: CoroutineScope = mainScope,
        onPreExecute: () -> Unit = {},
        onComplete: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onPostExecute: () -> Unit = {},
        params: Params? = null
    ): Job {
        job?.let {
            cancel()
        }

        return coroutineScope.launch {
            onPreExecute()

            try {
                val deferred = doWorkAsync(params)
                onComplete(deferred.await())
            } catch (throwable: CancellationException) {
                Logger.log(this@CoroutineUseCase, "The coroutine had canceled")
            } catch (throwable: Throwable) {
                Logger.logErr(throwable)
                onError(throwable)
                throwable.printStackTrace()
            }

            onPostExecute()
        }
    }
}