package com.merseyside.merseyLib.utils.core.ktor

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer
import io.ktor.client.request.setBody as builderSetBody

typealias Response = Any

fun HttpRequestBuilder.addHeader(key: String, value: Any) {
    header(key, value)
}

fun HttpRequestBuilder.addHeaders(vararg pairs: Pair<String, Any>) {
    pairs.forEach { pair -> addHeader(pair.first, pair.second) }
}

fun HttpRequestBuilder.setFormData(vararg pairs: Pair<String, Any>) {
    builderSetBody(FormDataContent(
        Parameters.build {
            pairs.forEach { pair -> append(pair.first, pair.second.toString()) }
        })
    )
}

inline fun <reified T : Any> HttpRequestBuilder.setBody(obj: T) {
    builderSetBody(obj.serialize())
}

fun HttpRequestBuilder.setJsonObjectAsBody(obj: JsonObject) {
    builderSetBody(obj.serialize())
}

fun HttpRequestBuilder.setJsonArrayAsBody(array: JsonArray) {
    builderSetBody(array.serialize())
}

fun HttpRequestBuilder.buildJsonObjectBody(builder: JsonObjectBuilder.() -> Unit) {
    setJsonObjectAsBody(buildJsonObject { builder() })
}

fun HttpRequestBuilder.buildJsonArrayBody(builder: JsonArrayBuilder.() -> Unit) {
    setJsonArrayAsBody(buildJsonArray { builder() })
}

suspend inline fun <reified T: Any> KtorRouter.post(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Post,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        builder = builder
    )
}

@Throws(Exception::class)
suspend inline fun <reified T: Any> KtorRouter.get(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Get,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        builder = builder
    )
}

@OptIn(InternalSerializationApi::class)
@Throws(Exception::class)
suspend inline fun <reified T: Any> KtorRouter.getList(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<List<T>>? = ListSerializer(T::class.serializer()),
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): List<T> {

    return request(
        HttpMethod.Get,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        builder = builder
    )
}


suspend inline fun <reified T: Any> KtorRouter.delete(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Delete,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        builder = builder
    )
}

suspend inline fun <reified T: Any> KtorRouter.put(
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): T {
    return request(
        HttpMethod.Put,
        path, *queryParams,
        deserializationStrategy = deserializationStrategy,
        builder = builder
    )
}

suspend inline fun <reified T: Any> KtorRouter.request(
    httpMethod: HttpMethod,
    path: String,
    vararg queryParams: Pair<String, String>,
    deserializationStrategy: DeserializationStrategy<T>? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): T {
    val call = client().request {
        buildUrl(httpMethod, path, *queryParams)
        builder()
    }
    
    return deserialize(call.body(), deserializationStrategy).also { handleResponse(it) }
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