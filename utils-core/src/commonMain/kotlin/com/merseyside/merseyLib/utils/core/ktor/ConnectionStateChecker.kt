package com.merseyside.merseyLib.utils.core.ktor

expect class ConnectionStateChecker {

    fun isOnline(): Boolean
}