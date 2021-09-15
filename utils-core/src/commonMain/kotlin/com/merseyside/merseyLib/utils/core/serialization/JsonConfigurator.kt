package com.merseyside.merseyLib.utils.core.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object JsonConfigurator {

    private val serializersModules: MutableList<SerializersModule> = mutableListOf()
    private var _json: Json? = null

    val json: Json
        get() {
            return _json ?: getDefaultJson().also { _json = it }
        }

    fun configure(block: JsonBuilder.() -> Unit) {
        _json = Json {
            block()
        }
    }

    fun addSerializerModules(module: SerializersModule) {
        serializersModules.add(module)
        _json = Json(from = json) {
            serializersModule += module
        }
    }

    private fun getDefaultJson() = Json {
        isLenient = true
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }

}