package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.kotlin.extensions.camelToSnakeCase
import com.merseyside.merseyLib.kotlin.extensions.isNotNullAndEmpty

fun <T: Any> String?.isNotNullAndEmpty(block: String.() -> T): T? {
    return if (isNotNullAndEmpty()) {
        this.block()
    } else {
        null
    }
}

fun String.camelToUpperSnakeCase(): String {
    return this.camelToSnakeCase().uppercase()
}

fun String.decodeBase64String(): String {
    return decodeBase64().toString()
}

expect fun String.encodeBase64(): String

expect fun String.decodeBase64(): ByteArray