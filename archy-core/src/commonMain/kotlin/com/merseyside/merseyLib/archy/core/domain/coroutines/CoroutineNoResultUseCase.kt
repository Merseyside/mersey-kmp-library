package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.utils.core.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class CoroutineNoResultUseCase<Params> : BaseCoroutineUseCase<Unit, Params>() {

    fun execute(
        coroutineScope: CoroutineScope = mainScope,
        onPreExecute: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onPostExecute: () -> Unit = {},
        params: Params? = null
    ) = coroutineScope.launch {
        onPreExecute()

        try {
            val deferred = doWorkAsync(params)
            deferred.await()
            onComplete.invoke()
        } catch (throwable: CancellationException) {
            Logger.log(this@CoroutineNoResultUseCase, "The coroutine had canceled")
        } catch (throwable: Throwable) {
            Logger.logErr(throwable)
            onError(throwable)
        }

        onPostExecute()
    }
}