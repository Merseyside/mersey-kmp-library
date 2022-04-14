package com.merseyside.merseyLib.utils.core.ktor

import android.annotation.SuppressLint
import android.os.Build
import com.merseyside.utils.BuildConfig
import com.merseyside.utils.network.InternetConnectionObserver
import com.merseyside.utils.network.NetworkStateListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("MissingPermission")
actual class ConnectionStateChecker(
    private val connectionObserver: InternetConnectionObserver
) {

    private val networkStateFlow = MutableStateFlow(false)

    //private val connectionObserver: InternetConnectionObserver
    private var currentState: Boolean = false
    private var isStarted: Boolean = false

    private val listener = object : NetworkStateListener {
        override fun onConnectionState(state: Boolean) {
            networkStateFlow.value = state
        }
    }

    actual fun isOnline(): Boolean {
        return networkStateFlow.value
    }

    actual fun start(): Flow<Boolean> {
        connectionObserver.registerReceiver(listener)
        isStarted = true
        return networkStateFlow
    }

    actual fun stop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            connectionObserver.unregisterReceiver(listener)
        }
        isStarted = false
    }

    protected actual fun getState(callback: (Boolean) -> Unit) {
    }
}