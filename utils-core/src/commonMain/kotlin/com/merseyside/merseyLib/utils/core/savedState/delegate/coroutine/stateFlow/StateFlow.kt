package com.merseyside.merseyLib.utils.core.savedState.delegate.coroutine.stateFlow

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.merseyLib.utils.core.savedState.ext.getSerializable
import com.merseyside.merseyLib.utils.core.savedState.ext.putSerializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T, F : StateFlow<T>> SavedState.stateFlow(scope: CoroutineScope, init: (value: T?) -> F) =
    object : ReadOnlyProperty<Any?, F> {

        private var stateFlow: F? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): F {
            return stateFlow ?: run {
                val value = get<T>(property.name)
                init(value)
            }.also { flow ->
                flow.onEach { value -> put(property.name, value) }.launchIn(scope)
            }
        }
    }

inline fun <reified T, F : StateFlow<T>> SavedState.serializableStateFlow(
    scope: CoroutineScope,
    crossinline init: (value: T?) -> F
) = object : ReadOnlyProperty<Any?, F> {

    private var stateFlow: F? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): F {
        return stateFlow ?: run {
            val value = getSerializable<T>(property.name)
            init(value)
        }.also { flow ->
            flow.onEach { value -> putSerializable(property.name, value) }.launchIn(scope)
        }
    }
}