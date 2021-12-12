package com.merseyside.merseyLib.utils.core.ktor

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.*

class ConnectionStateFeature private constructor(
    private val isConnected: () -> Boolean,
    private val onNoConnection: () -> Exception
) {

    class Config {
        var checkConnection: () -> Boolean = { false }
        var onNoConnection: () -> Exception = { throw Exception() }
        fun build() = ConnectionStateFeature(
            isConnected = checkConnection,
            onNoConnection = onNoConnection
        )
    }

    companion object Feature : HttpClientFeature<Config, ConnectionStateFeature> {
        override val key = AttributeKey<ConnectionStateFeature>("ConnectionStateFeature")

        override fun prepare(block: Config.() -> Unit) = Config().apply(block).build()

        override fun install(feature: ConnectionStateFeature, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                with(feature) {
                    if (!isConnected()) {
                        onNoConnection()
                    }
                }
            }
        }
    }
}