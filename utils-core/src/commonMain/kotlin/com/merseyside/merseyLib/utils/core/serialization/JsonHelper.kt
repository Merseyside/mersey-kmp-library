package com.merseyside.merseyLib.utils.core.serialization

import com.merseyside.merseyLib.utils.core.serialization.ext.toJsonArray
import kotlinx.serialization.json.JsonArray

fun ListStringConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListIntConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListFloatConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}

fun ListBooleanConverter.toJsonArray(): JsonArray {
    return asList().toJsonArray()
}