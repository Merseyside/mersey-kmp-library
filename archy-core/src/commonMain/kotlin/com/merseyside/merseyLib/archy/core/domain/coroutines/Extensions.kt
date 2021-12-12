package com.merseyside.merseyLib.archy.core.domain.coroutines

import com.merseyside.merseyLib.archy.core.domain.coroutines.exception.NoParamsException
import com.merseyside.merseyLib.kotlin.Logger
import kotlinx.coroutines.*

suspend fun debounce(
    waitMs: Long = 300L,
    destinationFunction: suspend () -> Unit
) {
    delay(waitMs)
    destinationFunction.invoke()
}

inline fun <Params, Result> requireParams(
    params: Params?,
    block: Params.() -> Result
): Result {
    return if (params != null) {
        block(params)
    } else throw NoParamsException()
}

fun <R1, P1, R2, P2> CoroutineScope.zipUseCases(
    c1: BaseCoroutineUseCase<R1, P1>,
    c2: BaseCoroutineUseCase<R2, P2>,
    p1: P1? = null,
    p2: P2? = null,
    onError: (error: Throwable) -> Unit = {},
    onComplete: (R1, R2) -> Unit = { _, _  -> },
): Job {
    return launch {
        val deffered1 = async { c1(p1) }
        val deffered2 = async { c2(p2) }

        try {
            onComplete(deffered1.await(), deffered2.await())
        } catch (throwable: Throwable) {
            onError(throwable)
            Logger.logErr(throwable)
        }
    }
}

fun <R1, P1, R2, P2, R3, P3> CoroutineScope.zipUseCases(
    c1: BaseCoroutineUseCase<R1, P1>,
    c2: BaseCoroutineUseCase<R2, P2>,
    c3: BaseCoroutineUseCase<R3, P3>,
    p1: P1? = null,
    p2: P2? = null,
    p3: P3? = null,
    onError: (error: Throwable) -> Unit = {},
    onComplete: (R1, R2, R3) -> Unit = { _, _, _  -> },
): Job {
    return launch {
        val deffered1 = async { c1(p1) }
        val deffered2 = async { c2(p2) }
        val deffered3 = async { c3(p3) }

        try {
            onComplete(deffered1.await(), deffered2.await(), deffered3.await())
        } catch (throwable: Throwable) {
            onError(throwable)
            Logger.logErr(throwable)
        }
    }
}

