package com.merseyside.merseyLib.archy.core.di.state

import com.merseyside.merseyLib.utils.core.state.SavedState
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

typealias StateDefinition<T> = Scope.(SavedState, ParametersHolder) -> T

fun getStateKey(clazz: KClass<*>): String {
    return "${clazz.simpleName}_state_key"
}

inline fun <reified T> getStateKey(): String {
    return "${T::class.simpleName}_state_key"
}