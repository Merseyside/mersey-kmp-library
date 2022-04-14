package com.merseyside.merseyLib.utils.core.ktor

import cocoapods.Reachability.*

actual class ConnectionStateChecker {

    private val connectionState: Boolean = false

    init {
        val reachability: Reachability
    }

    actual fun isOnline(): Boolean {
        TODO("Not yet implemented")
    }
}