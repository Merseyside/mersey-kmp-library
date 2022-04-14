package com.merseyside.merseyLib.utils.core.ktor

import kotlinx.coroutines.flow.Flow

expect class ConnectionStateChecker {

    fun start(): Flow<Boolean>
    fun stop()

    /**
     * Check network connectivity
     */
    fun isOnline(): Boolean

    protected fun getState(callback: (Boolean) -> Unit)
}