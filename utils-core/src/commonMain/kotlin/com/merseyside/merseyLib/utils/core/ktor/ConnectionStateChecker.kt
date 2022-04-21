package com.merseyside.merseyLib.utils.core.ktor

import kotlinx.coroutines.flow.Flow


expect class ConnectionStateChecker {

    val networkStateFlow: Flow<Boolean>

    fun start()
    fun stop()

    /**
     * Check network connectivity
     */
    fun isOnline(): Boolean
    fun addNetworkStateCallback(callback: (Boolean) -> Unit)
}