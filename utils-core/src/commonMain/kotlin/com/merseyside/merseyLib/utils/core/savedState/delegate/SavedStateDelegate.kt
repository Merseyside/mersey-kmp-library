package com.merseyside.merseyLib.utils.core.savedState.delegate

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> SavedState.value(defValue: T) = object : ReadWriteProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: getOrPut(property.name, defValue).also {
            value = it
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        put(property.name, value)
    }

}

fun <T> SavedState.valueOrNull() = object : ReadWriteProperty<Any, T?> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return value ?: get<T>(property.name)?.also {
            value = it
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        put(property.name, value)
    }

}