package com.merseyside.merseyLib.utils.core.koin.scope.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.koin.core.Koin
import org.koin.core.scope.Scope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LifecycleSharedScopeDelegate(
    val koin: Koin,
    val lifecycleOwner: LifecycleOwner,
    private val provideScope: (Koin) -> Scope
) : ReadOnlyProperty<LifecycleOwner, Scope> {

    private var _scope: Scope? = null

    init {
        val logger = koin.logger

        logger.debug("setup scope: $_scope for $lifecycleOwner")
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                createScope()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                logger.debug("Closing scope: $_scope for $lifecycleOwner")
                if (_scope?.closed == false) {
                    _scope?.close()
                }
                _scope = null
            }
        })
    }

    private fun createScope() {
        if (_scope == null) {
            koin.logger.debug("Create scope: $_scope for $lifecycleOwner")
            _scope = provideScope(koin)
        }
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): Scope {
        return if (_scope == null) {
            createScope()
            return _scope ?: error("can't get Scope for $lifecycleOwner")
        }
        else {
            _scope ?: error("can't get Scope for $lifecycleOwner")
        }
    }
}