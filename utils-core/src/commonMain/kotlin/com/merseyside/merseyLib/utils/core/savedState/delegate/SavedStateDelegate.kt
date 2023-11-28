package com.merseyside.merseyLib.utils.core.savedState.delegate

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.merseyLib.utils.core.savedState.ext.getSerializable
import com.merseyside.merseyLib.utils.core.savedState.ext.putSerializable
import kotlinx.serialization.KSerializer
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T> SavedState.saveable(crossinline init: (SavedState) -> T) =
    object : ReadOnlyProperty<Any, T> {
        private var _value: T? = null

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return _value ?: run {
                val savedState = getSavedState(property.name)
                init(savedState).also { _value = it }
            }
        }
    }

/**
 * Only for primitives.
 */
fun <T> SavedState.value(defValue: T) = object : ReadWriteProperty<Any, T> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return _value ?: getOrPut(property.name, defValue).also { _value = it }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        _value = put(property.name, value)
    }
}

fun <T> SavedState.valueOrNull() = object : ReadWriteProperty<Any, T?> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return _value ?: get<T>(property.name)?.also { _value = it }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        _value = put(property.name, value)
    }
}

/* Serializable */
inline fun <reified T> SavedState.serializable(
    defValue: T,
    serializer: KSerializer<T>? = null
) = object : ReadWriteProperty<Any, T> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val key = property.name

        return _value ?: run {
            serializer?.let {
                getSerializable(key, defValue, it)
            } ?: getSerializable(key, defValue)
        }.also { _value = it }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val key = property.name
        serializer?.let { putSerializable(key, it) } ?: putSerializable(key, value)
        _value = value
    }
}

inline fun <reified T> SavedState.serializableOrNull(
    serializer: KSerializer<T>? = null
) = object : ReadWriteProperty<Any, T?> {
    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return _value ?: run {
            val key = property.name
            serializer?.let { ser ->
                getSerializable(key, ser)
            } ?: getSerializable(key)
        }.also { value -> _value = value }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val key = property.name
        serializer?.let { putSerializable(key, it) } ?: putSerializable(key, value)
        _value = value
    }
}