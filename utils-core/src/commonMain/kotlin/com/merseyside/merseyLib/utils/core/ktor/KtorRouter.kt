package com.merseyside.merseyLib.utils.core.ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

abstract class KtorRouter(
    val client: () -> HttpClient,
    val json: Json = createJson(),
    val baseUrl: () -> String = { "" }
) {

    var isEncoding = false

    open fun handleResponse(response: Response) {}

    @OptIn(InternalAPI::class)
    fun HttpRequestBuilder.buildUrl(
        method: HttpMethod,
        path: String,
        vararg queryParams: Pair<String, String>
    ) {
        this.method = method
        url {
            baseUrl().let { if (it.isNotEmpty()) host = it }
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