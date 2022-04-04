package com.merseyside.merseyLib.utils.core.ktor

expect class ConnectionStateChecker {

    /**
     * Check network connectivity
     */
    fun isOnline(): Boolean
}