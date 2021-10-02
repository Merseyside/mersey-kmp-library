package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.archy.core.domain.coroutines.exception.NoParamsException
import kotlinx.coroutines.delay

suspend fun debounce(
    waitMs: Long = 300L,
    destinationFunction: suspend () -> Unit
) {
    delay(waitMs)
    destinationFunction.invoke()
}

inline fun <Params, Result> BaseCoroutineUseCase<*, Params>.requireParams(
    params: Params?,
    block: Params.() -> Result
): Result {
    return if (params != null) {
        block(params)
    } else throw NoParamsException()
}

