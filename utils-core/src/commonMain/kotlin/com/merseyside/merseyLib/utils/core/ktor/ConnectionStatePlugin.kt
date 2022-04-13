package com.merseyside.merseyLib.utils.core.ktor

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.util.*

class ConnectionStatePlugin private constructor(
    private val isConnected: () -> Boolean,
    private val onNoConnection: () -> Exception
) {

    class Config {
        var checkConnection: () -> Boolean = { false }
        var onNoConnection: () -> Exception = { throw Exception() }
        fun build() = ConnectionStatePlugin(
            isConnected = checkConnection,
            onNoConnection = onNoConnection
        )
    }

    companion object Plugin : HttpClientPlugin<Config, ConnectionStatePlugin> {
        override val key = AttributeKey<ConnectionStatePlugin>("ConnectionStatePlugin")

        override fun prepare(block: Config.() -> Unit) = Config().apply(block).build()

        override fun install(plugin: ConnectionStatePlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                with(plugin) {
                    if (!isConnected()) {
                        onNoConnection()
                    }
                }
            }
        }
    }
}