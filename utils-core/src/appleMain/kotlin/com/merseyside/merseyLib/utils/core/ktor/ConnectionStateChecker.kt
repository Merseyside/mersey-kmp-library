package com.merseyside.merseyLib.utils.core.ktor

//import cocoapods.Reachability.Reachability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

actual class ConnectionStateChecker {

    private val mutFlow = MutableStateFlow(false)
    actual val networkStateFlow: Flow<Boolean> = mutFlow
//    private lateinit var reachability: Reachability

    actual fun isOnline(): Boolean {
        TODO()
        //return reachability.isReachable()
    }

    actual fun start() {
//        reachability = Reachability.reachabilityForInternetConnection()
//            ?: throw Exception("Can not init reachability")
//
//        val reachableCallback = { _: Reachability? ->
//            mutFlow.update { true }
//        }
//        reachability.reachableBlock = reachableCallback
//
//        val unreachableCallback = { _: Reachability? ->
//            mutFlow.update { false }
//        }
//        reachability.unreachableBlock = unreachableCallback
//
//        reachability.startNotifier()
//        mutFlow.update { reachability.isReachable() }
    }

    actual fun stop() {
        //reachability.stopNotifier()
    }

    actual fun addNetworkStateCallback(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            networkStateFlow.collect {
                callback(it)
            }
        }
    }

    companion object {
        private const val TAG = "ConnectionStateChecker"
    }
}