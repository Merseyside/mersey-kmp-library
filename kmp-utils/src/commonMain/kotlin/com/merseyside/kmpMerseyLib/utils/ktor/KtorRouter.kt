package com.merseyside.kmpMerseyLib.utils.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.http.*
import kotlinx.serialization.json.Json

abstract class KtorRouter(
    val client: HttpClient,
    val json: Json = createJson(),
    val baseUrl: String? = null
) {
    constructor(
        httpClientEngine: HttpClientEngine,
        baseUrl: String,
        json: Json = createJson(),
        defaultRequest: HttpRequestBuilder.() -> Unit = {}
    ) : this(
        httpClientEngine.run {
            HttpClient(httpClientEngine) {
                defaultRequest {
                    accept(ContentType.Application.Json)
                    defaultRequest()
                }
            }
        }, json, baseUrl
    )

    constructor(
        baseUrl: String,
        json: Json = createJson(),
        defaultRequest: HttpRequestBuilder.() -> Unit = {}
    ) : this(
        HttpClient {
            defaultRequest {
                accept(ContentType.Application.Json)
                defaultRequest()
            }
        }, json, baseUrl
    )

    var isEncoding = false

    open fun handleResponse(response: Response) {}

    fun HttpRequestBuilder.buildUrl(
        method: HttpMethod,
        path: String,
        vararg queryParams: Pair<String, String>
    ) {
        this.method = method
        url {
            baseUrl?.let { host = it }
            encodedPath = path
            queryParams.forEach { parameters.append(it.first, it.second) }
        }
    }

    fun getRoute(method: String, vararg queryParams: Pair<String, String>): String {
        val uri = "$baseUrl/$method"

        return if (queryParams.isNotEmpty()) {
            val uriQueryBuilder = URIQueryBuilder(isEncoding)
            uriQueryBuilder.addParams(queryParams.toMap())

            uriQueryBuilder.addQueryToUri(uri)
        } else {
            uri
        }
    }

    companion object {
        fun createJson(): Json {
            return Json {
                isLenient = false
                ignoreUnknownKeys = true
            }
        }
    }
}