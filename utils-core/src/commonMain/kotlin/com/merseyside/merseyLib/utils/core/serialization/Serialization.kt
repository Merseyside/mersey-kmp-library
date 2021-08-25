package com.merseyside.merseyLib.utils.core.serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

//val module = SerializersModule {
//    truePolymorphic(TimeUnit::class) {
//        subclass(Millis::class)
//    }
//}

val json: Json by lazy {
    Json {
        isLenient = true
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
        //serializersModule = module
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

@OptIn(InternalSerializationApi::class)
fun <T : Any> T.serialize(type: KClass<T>): String {
    return serialize(type.serializer())
}

fun <T : Any> T.serialize(serializationStrategy: SerializationStrategy<T>): String {
    return json.encodeToString(serializationStrategy, this)
}

fun <T> String.deserialize(deserializationStrategy: DeserializationStrategy<T>): T {
    return json.decodeFromString(deserializationStrategy, this)
}

inline fun <reified T : Any> String.deserialize(): T {
    return json.decodeFromString(this)
}

@OptIn(InternalSerializationApi::class)
fun <T : Any> String.deserialize(type: KClass<T>): T {
    return deserialize(type.serializer())
}


inline fun <reified T : Any> Any.deserialize(): T {
    return this.toString().deserialize()
}

fun <T> Any.deserialize(deserializationStrategy: DeserializationStrategy<T>): T {
    return this.toString().deserialize(deserializationStrategy)
}


