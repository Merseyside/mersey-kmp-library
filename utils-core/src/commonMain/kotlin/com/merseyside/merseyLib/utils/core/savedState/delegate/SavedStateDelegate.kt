package com.merseyside.merseyLib.utils.core.savedState.delegate

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> SavedState.saveable(init: (SavedState) -> T) = object : ReadOnlyProperty<Any, T> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return _value ?: run {
            val savedState = getSavedState(property.name)
            init(savedState).also { _value = it }
        }
    }
}

fun <T> SavedState.value(defValue: T) = object : ReadWriteProperty<Any, T> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return _value ?: getOrPut(property.name, defValue).also {
            _value = it
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        _value = put(property.name, value)
    }

}

fun <T> SavedState.valueOrNull() = object : ReadWriteProperty<Any, T?> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return _value ?: get<T>(property.name)?.also {
            _value = it
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        _value = put(property.name, value)
    }

}