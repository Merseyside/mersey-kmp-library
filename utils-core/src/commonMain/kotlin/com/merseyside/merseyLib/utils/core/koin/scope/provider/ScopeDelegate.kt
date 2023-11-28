package com.merseyside.merseyLib.utils.core.koin.scope.provider

import com.merseyside.merseyLib.kotlin.extensions.isNotNullAndEmpty
import com.merseyside.merseyLib.kotlin.logger.log
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback

abstract class ScopeProvider(
    val koin: Koin,
    private val modules: List<Module>,
    private val provideScope: (Koin) -> Scope?,
    protected val createScope: (Koin) -> Scope
) {

    protected var scope: Scope? = null
    private var scopeModules = emptyList<Module>()

    private fun createScope(): Scope {
        //scopeModules = modules.filter { module -> !module.isLoaded.log("scope", "isLoaded =") }

        koin.loadModules(modules)
        //scopeModules.isNotNullAndEmpty { koin.loadModules(this) }
        return createScope(koin).also { scope ->
            this.scope = scope
            onScopeCreated(scope)
        }
    }

    fun close() {
        if (scope != null) {
            scope?.close()
            onScopeClosed(requireNotNull(scope))
            scope = null
        }
    }

    abstract fun onScopeClosed(scope: Scope)

    abstract fun onScopeCreated(scope: Scope)

    internal fun getValue(): Scope {
        return if (scope == null || scope?.closed == true) {
            provideScope(koin) ?: createScope().also { scope ->
                scope.registerCallback(object : ScopeCallback {
                    override fun onScopeClose(scope: Scope) {
                        //scopeModules.isNotNullAndEmpty { koin.unloadModules(this) }
                        //koin.unloadModules(modules)
                        onScopeClosed(scope)
                    }
                })
            }
        } else {
            scope ?: error("Can not get scope!")
        }
    }
}

