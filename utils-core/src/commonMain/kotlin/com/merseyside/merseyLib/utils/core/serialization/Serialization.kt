package com.merseyside.merseyLib.utils.core.serialization

import com.merseyside.merseyLib.time.Millis
import com.merseyside.merseyLib.time.TimeUnit
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.modules.polymorphic as truePolymorphic

val module = SerializersModule {
    truePolymorphic(TimeUnit::class) {
        subclass(Millis::class)
    }
}

val json: Json by lazy {
    Json {
        isLenient = true
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
        serializersModule = module
    }
}

inline fun <reified T : Any> T.serialize(): String {
    return json.encodeToString(this)
}

inline fun <reified T : Any> T.serializeLong(): Long {
    return json.encodeToString(this).toLong()
}

inline fun <reified T : Any> T.serializeInt(): Int {
    return json.encodeToString(this).toInt()
}

inline fun <reified T : Any> T.serializeFloat(): Float {
    return json.encodeToString(this).toFloat()
}

inline fun <reified T : Any> String.deserialize(): T {
    return json.decodeFromString(this)
}

fun <T : Any> T.serialize(serializationStrategy: SerializationStrategy<T>): String {
    return json.encodeToString(serializationStrategy, this)
}

fun <T> String.deserialize(deserializationStrategy: DeserializationStrategy<T>): T {
    return json.decodeFromString(deserializationStrategy, this)
}

inline fun <reified T : Any> Any.deserialize(): T {
    return this.toString().deserialize()
}

fun <T> Any.deserialize(deserializationStrategy: DeserializationStrategy<T>): T {
    return this.toString().deserialize(deserializationStrategy)
}


