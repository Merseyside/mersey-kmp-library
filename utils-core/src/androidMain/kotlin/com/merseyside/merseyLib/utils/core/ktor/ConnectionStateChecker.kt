package com.merseyside.merseyLib.utils.core.ktor

import android.annotation.SuppressLint
import com.merseyside.utils.network.InternetConnectionObserver
import com.merseyside.utils.network.NetworkStateListener
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
actual class ConnectionStateChecker(
    private val connectionObserver: InternetConnectionObserver
) {
    private val mutFlow = MutableStateFlow(false)
    actual val networkStateFlow: Flow<Boolean> = mutFlow

    private val listener = object : NetworkStateListener {
        override fun onConnectionState(state: Boolean) {
            mutFlow.value = state
        }
    }

    actual fun isOnline(): Boolean {
        return connectionObserver.isOnline()
    }

    actual fun start() {
        connectionObserver.addNetworkStateListener(listener)
    }

    actual fun stop() {
        connectionObserver.removeNetworkStateListeners()
    }

    actual fun addNetworkStateCallback(callback: (Boolean) -> Unit) {
        MainScope().launch {
            networkStateFlow.collect {
                callback(it)
            }
        }
    }
}