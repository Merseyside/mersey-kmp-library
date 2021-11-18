package com.merseyside.merseyLib.utils.core.ktor

import android.annotation.SuppressLint
import com.merseyside.utils.network.InternetConnectionObserver
import com.merseyside.utils.network.NetworkStateListener

@SuppressLint("MissingPermission")
actual class ConnectionStateChecker {

    private val connectionObserver: InternetConnectionObserver
    private var currentState: Boolean = false

    constructor(connectionObserver: InternetConnectionObserver) {
        this.connectionObserver = connectionObserver.apply {
            registerReceiver(object : NetworkStateListener {
                override fun onConnectionState(state: Boolean) {
                    currentState = state
                }
            })

        }
    }

    actual fun isOnline() = currentState
}