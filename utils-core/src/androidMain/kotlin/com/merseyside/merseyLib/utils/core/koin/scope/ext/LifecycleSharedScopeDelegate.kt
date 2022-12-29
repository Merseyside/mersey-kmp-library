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
    private val provideScope: (Koin) -> Scope?,
    private val createScope: (Koin) -> Scope
) : ReadOnlyProperty<LifecycleOwner, Scope> {

    private var _scope: Scope? = null

    private fun createScope(): Scope {
        koin.logger.debug("Create scope: $_scope for $lifecycleOwner")
        return this.createScope.invoke(koin).also { scope ->
            lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

                override fun onDestroy(owner: LifecycleOwner) {
                    koin.logger.debug("Closing scope: $_scope for $lifecycleOwner")
                    if (!scope.closed) {
                        scope.close()
                    }
                    _scope = null
                }
            })
        }
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): Scope {
        return if (_scope == null) {
            val _scope = provideScope(koin) ?: createScope()
            return _scope
        }
        else {
            _scope ?: error("can't get Scope for $lifecycleOwner")
        }
    }
}