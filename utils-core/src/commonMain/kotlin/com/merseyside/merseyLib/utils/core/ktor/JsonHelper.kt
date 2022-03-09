package com.merseyside.merseyLib.utils.core.ktor

import com.merseyside.merseyLib.time.units.TimeUnit
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.put

fun JsonObjectBuilder.put(key: String, timeUnit: TimeUnit): JsonElement? {
    return put(key, timeUnit.value)
}

fun JsonObjectBuilder.putSafe(key: String, timeUnit: TimeUnit?): JsonElement? {
    return timeUnit?.let { put(key, timeUnit) }
}