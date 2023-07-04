package com.merseyside.merseyLib.utils.core.settings

import com.merseyside.merseyLib.kotlin.serialization.JsonConfigurator
import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import com.russhwolf.settings.Settings
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

inline fun <reified T : Any> Settings.putSerializable(
    key: String,
    value: T,
    json: Json = JsonConfigurator.json
) {
    val str = value.serialize(json)
    putString(key, str)
}

inline fun <reified T : Any> Settings.putSerializable(
    key: String,
    value: T,
    serializationStrategy: SerializationStrategy<T>,
    json: Json = JsonConfigurator.json
) {
    val str = value.serialize(serializationStrategy, json)
    putString(key, str)
}

inline fun <reified T : Any> Settings.getSerializable(
    key: String,
    defaultValue: T,
    json: Json = JsonConfigurator.json
): T {
    return getStringOrNull(key)?.deserialize(json) ?: defaultValue
}

inline fun <reified T : Any> Settings.getSerializable(
    key: String,
    defaultValue: T,
    deserializationStrategy: DeserializationStrategy<T>,
    json: Json = JsonConfigurator.json
): T {
    return getStringOrNull(key)?.deserialize(deserializationStrategy, json) ?: defaultValue
}

inline fun <reified T : Any> Settings.getSerializableOrNull(
    key: String,
    json: Json = JsonConfigurator.json
): T? {
    return getStringOrNull(key)?.deserialize(json)
}

inline fun <reified T : Any> Settings.getSerializableOrNull(
    key: String,
    deserializationStrategy: DeserializationStrategy<T>,
    json: Json = JsonConfigurator.json
): T? {
    return getStringOrNull(key)?.deserialize(deserializationStrategy, json)
}