package com.merseyside.merseyLib.utils.core.serialization

import com.merseyside.merseyLib.utils.core.serialization.ext.toJsonArray
import com.merseyside.merseyLib.utils.core.serialization.ext.toJsonObject
import com.merseyside.merseyLib.utils.core.serialization.ext.toJsonPrimitive
import kotlinx.serialization.json.*

fun ListStringConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListIntConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListFloatConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListBooleanConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

inline fun <reified T : Any> JsonObjectBuilder.putSerializable(
    key: String,
    obj: T,
    json: Json = JsonConfigurator.json
): JsonElement? {
    return put(key, obj.toJsonObject(json))
}

inline fun <reified T : Any, reified R : Any> JsonObjectBuilder.putPrimitiveSerializable(
    key: String,
    obj: T,
    json: Json = JsonConfigurator.json
): JsonElement? {
    val primitive = obj.toJsonPrimitive(json)
    return when (R::class) {
        Int::class -> put(key, primitive.intOrNull)
        Float::class -> put(key, primitive.floatOrNull)
        String::class -> put(key, primitive)
        else -> throw Exception("Passed type is not primitive or not support.")
    }
}

fun JsonObjectBuilder.putSafe(key: String, value: Number?): JsonElement? {
    return value?.let { put(key, value) }
}

fun JsonObjectBuilder.putSafe(key: String, value: String?): JsonElement? {
    return value?.let { put(key, value) }
}

fun JsonObjectBuilder.putSafe(key: String, value: JsonElement?): JsonElement? {
    return value?.let { put(key, value) }
}