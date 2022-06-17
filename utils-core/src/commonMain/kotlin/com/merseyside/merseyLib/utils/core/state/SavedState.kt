package com.merseyside.merseyLib.utils.core.state

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

open class SavedState {

    internal val container: MutableMap<String, Any> = HashMap()

    fun addAll(map: Map<String, Any>) {
        if (map.isNotEmpty()) {
            this.container.putAll(map)
        }
    }

    fun contains(key: String): Boolean {
        return container.contains(key)
    }

    fun put(key: String, value: Any) {
        container[key] = value
    }

    inline fun <reified T : Any> putSerializable(key: String, value: T) {
        put(key, value.serialize())
    }

    inline fun <reified T : Any> putSerializable(
        key: String,
        value: T,
        serializationStrategy: SerializationStrategy<T>
    ) {
        put(key, value.serialize(serializationStrategy))
    }

    fun getString(key: String): String? {
        return container[key] as? String
    }

    fun getBool(key: String): Boolean? {
        return container[key] as? Boolean
    }

    fun getInt(key: String): Int? {
        return container[key] as? Int
    }

    fun getLong(key: String): Long? {
        return container[key] as? Long
    }

    fun getFloat(key: String): Float? {
        return container[key] as? Float
    }

    fun get(key: String): Any? {
        return container[key]
    }

    fun getSavedState(key: String): SavedState? {
        return if (contains(key)) {
            val value = get(key)
            value as? SavedState ?: throw IllegalArgumentException("Value by $key key is not SavedState! \n $value")
        } else null
    }

    inline fun <reified T : Any> getSerializable(key: String): T? {
        return get(key)?.deserialize()
    }

    @Throws(NullPointerException::class)
    inline fun <reified T : Any> getSerializableOrThrow(key: String): T {
        return getSerializable(key) ?: throw NullPointerException()
    }

    inline fun <reified T : Any> getSerializable(
        key: String,
        deserializationStrategy: DeserializationStrategy<T>
    ): T? {
        return get(key)?.deserialize(deserializationStrategy)
    }

    override fun toString(): String {
        return container.map { "${it.key} : ${it.value}"}.joinToString(separator = "\n")
    }
}


fun DummySavedState() = SavedState()