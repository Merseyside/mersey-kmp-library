package com.merseyside.merseyLib.archy.core.domain.coroutines

import kotlinx.coroutines.*

abstract class BaseCoroutineUseCase<T, Params> {

    protected val mainScope: CoroutineScope by lazy { CoroutineScope(applicationContext) }

    private val asyncJob = SupervisorJob()
    private val scope = CoroutineScope(asyncJob)

    var job: Job? = null
        set(value) {
            field?.let {
                if (it.isActive) {
                    it.cancel()
                }
            }

            field = value
        }

    val isActive: Boolean
        get() {
            return job?.isActive ?: false
        }

    protected abstract suspend fun executeOnBackground(params: Params?): T

    protected suspend fun doWorkAsync(params: Params?): Deferred<T> = coroutineScope {
        async(asyncJob) {
            executeOnBackground(params)
        }.also { job = it }
    }

    fun cancel(cause: CancellationException? = null): Boolean {
        return job?.let {
            it.cancel(cause)
            job = null
            true
        } ?: false
    }

    suspend operator fun invoke(params: Params? = null) = executeOnBackground(params)
}