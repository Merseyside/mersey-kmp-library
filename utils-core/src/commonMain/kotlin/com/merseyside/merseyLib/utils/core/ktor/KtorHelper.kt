package com.merseyside.merseyLib.utils.core.ktor

import com.merseyside.merseyLib.utils.core.serialization.deserialize
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.serializer

typealias Response = Any

fun HttpRequestBuilder.addHeader(key: String, value: Any) {
    header(key, value)
}

fun HttpRequestBuilder.addHeaders(vararg pairs: Pair<String, Any>) {
    pairs.forEach { pair -> addHeader(pair.first, pair.second) }
}

fun HttpRequestBuilder.setFormData(vararg pairs: Pair<String, Any>) {
    body = FormDataContent(
        Parameters.build {
            pairs.forEach { pair -> append(pair.first, pair.second.toString()) }
        })
}

suspend inline fun <reified T: Any> KtorRouter.post(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Post,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        block = block
    )
}

@Throws(Exception::class)
suspend inline fun <reified T: Any> KtorRouter.get(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Get,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        block = block
    )
}

@OptIn(InternalSerializationApi::class)
@Throws(Exception::class)
suspend inline fun <reified T: Any> KtorRouter.getList(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<List<T>>? = ListSerializer(T::class.serializer()),
    block: HttpRequestBuilder.() -> Unit = {}
): List<T> {

    return request(
        HttpMethod.Get,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        block = block
    )
}


suspend inline fun <reified T: Any> KtorRouter.delete(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Delete,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        block = block
    )
}

suspend inline fun <reified T: Any> KtorRouter.put(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Put,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        block = block
    )
}

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified T: Any> KtorRouter.request(
    httpMethod: HttpMethod,
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    block: HttpRequestBuilder.() -> Unit = {}
): T {
    val call = client().request<String> {
        buildUrl(httpMethod, path, *queryParams)
        block()
    }
    
    return deserialize(call, deserializationStrategy).also { handleResponse(it) }
}

inline fun <reified T: Any> KtorRouter.deserialize(
    data: String,
    deserializationStrategy: DeserializationStrategy<T>? = null
): T {

    return if (deserializationStrategy != null) {
        data.deserialize(deserializationStrategy)
    } else {
        json.decodeFromString(data)
    }
}