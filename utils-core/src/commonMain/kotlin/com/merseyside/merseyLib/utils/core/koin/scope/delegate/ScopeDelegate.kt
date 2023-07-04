package com.merseyside.merseyLib.utils.core.koin.scope.delegate

import org.koin.core.Koin
import org.koin.core.scope.Scope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class ScopeDelegate(
    val koin: Koin,
    private val provideScope: (Koin) -> Scope?,
    protected val createScope: (Koin) -> Scope
) : ReadOnlyProperty<Any, Scope> {

    protected var scope: Scope? = null

    private fun createScope(): Scope {
        koin.logger.debug("Create scope: $scope")
        return createScope(koin).also { onScopeCreated(it) }
    }

    abstract fun onScopeCreated(scope: Scope)

    override fun getValue(thisRef: Any, property: KProperty<*>): Scope {
        return if (scope == null || scope?.isNotClosed() == false) {
            provideScope(koin) ?: createScope()
        }
        else {
            scope ?: error("Can not get scope!")
        }
    }
}

