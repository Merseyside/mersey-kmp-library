package com.merseyside.merseyLib.utils.core.savedState.ext

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

inline fun <reified T> SavedState.putSerializable(key: String, value: T) {
    put(key, value?.serialize())
}

inline fun <reified T> SavedState.putSerializable(
    key: String,
    value: T,
    serializationStrategy: SerializationStrategy<T>
) {
    put(key, value?.serialize(serializationStrategy))
}

inline fun <reified T> SavedState.getSerializable(
    key: String,
): T? {
    return get<String>(key)?.deserialize()
}

inline fun <reified T> SavedState.getSerializable(
    key: String,
    deserializationStrategy: DeserializationStrategy<T>
): T? {
    return get<String>(key)?.deserialize(deserializationStrategy)
}