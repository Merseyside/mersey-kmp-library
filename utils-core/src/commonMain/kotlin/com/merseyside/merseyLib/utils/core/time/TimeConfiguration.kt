package com.merseyside.merseyLib.utils.core.time

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object TimeConfiguration {

    var timeZone: String = TimeZone.SYSTEM.toString()

    var formatPatterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ssZ"
    )

    fun addFormatPattern(pattern: String) {
        formatPatterns = formatPatterns.toMutableList().apply { add(pattern) }
    }
}