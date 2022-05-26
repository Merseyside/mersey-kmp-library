package com.merseyside.merseyLib.utils.core.state

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

open class SavedState {

    val map: MutableMap<String, String> = HashMap()

    fun addAll(map: Map<String, String>) {
        if (map.isNotEmpty()) {
            this.map.putAll(map)
        }
    }

    fun getAll(): Map<String, String> = map

    fun contains(key: String): Boolean {
        return map.contains(key)
    }

    fun put(key: String, value: String) {
        map[key] = value
    }

    fun put(key: String, value: Any) {
        map[key] = value.toString()
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
        return map[key]
    }

    fun getBool(key: String): Boolean? {
        return map[key]?.toBooleanStrictOrNull()
    }

    fun getInt(key: String): Int? {
        return map[key]?.toIntOrNull()
    }

    fun getLong(key: String): Long? {
        return map[key]?.toLongOrNull()
    }

    fun getFloat(key: String): Float? {
        return map[key]?.toFloatOrNull()
    }

    inline fun <reified T : Any> getSerializable(key: String): T? {
        return map[key]?.deserialize()
    }

    @Throws(NullPointerException::class)
    inline fun <reified T : Any> getSerializableOrThrow(key: String): T {
        return getSerializable(key) ?: throw NullPointerException()
    }

    inline fun <reified T : Any> getSerializable(
        key: String,
        deserializationStrategy: DeserializationStrategy<T>
    ): T? {
        return map[key]?.deserialize(deserializationStrategy)
    }

    override fun toString(): String {
        return map.serialize(MapSerializer(String.serializer(), String.serializer()))
    }
}


fun DummySavedState() = SavedState()