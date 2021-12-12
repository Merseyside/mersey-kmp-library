package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.archy.core.domain.coroutines.exception.NoParamsException
import com.merseyside.merseyLib.kotlin.Logger
import kotlinx.coroutines.*

abstract class CoroutineUseCase<T, Params> : BaseCoroutineUseCase<T, Params>() {

    fun execute(
        coroutineScope: CoroutineScope = mainScope,
        onPreExecute: () -> Unit = {},
        onComplete: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onPostExecute: () -> Unit = {},
        params: Params? = null
    ) = coroutineScope.launch {
        onPreExecute()
        try {
            val deferred = doWorkAsync(params)
            onComplete(deferred.await())
        } catch (exception: CancellationException) {
            Logger.logErr(this@CoroutineUseCase, "The coroutine had canceled")
        } catch (exception: NoParamsException) {
            throw exception
        } catch (throwable: Throwable) {
            Logger.logErr(throwable)
            onError(throwable)
            throwable.printStackTrace()
        }

        onPostExecute()
    }
}