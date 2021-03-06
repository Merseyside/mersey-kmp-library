package com.merseyside.kmpMerseyLib.arch.domain.coroutines

import com.merseyside.kmpMerseyLib.utils.Logger
import kotlinx.coroutines.*

abstract class CoroutineUseCase<T, Params> : BaseCoroutineUseCase<T, Params>() {

    fun execute(
        coroutineScope: CoroutineScope = mainScope,
        onPreExecute: () -> Unit = {},
        onComplete: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onPostExecute: () -> Unit = {},
        params: Params? = null
    ) {
        if (job != null) {
            cancel()
        }

        coroutineScope.launch {
            onPreExecute()

            val deferred = doWorkAsync(params)

            try {
                onComplete.invoke(deferred.await())
            } catch (throwable: CancellationException) {
                Logger.log(this@CoroutineUseCase, "The coroutine had canceled")
            } catch (throwable: Throwable) {
                Logger.logErr(throwable)
                onError(throwable)
            }

            onPostExecute()
        }
    }
}