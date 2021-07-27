package com.merseyside.merseyLib.utils.core.time

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object TimeConfiguration {

    var timeZone: String = TimeZone.SYSTEM.toString()

    var language: Language = "en"
    var country: Country = "US"
    var formatPattern: String = "dd-MM-YYYY hh:mm"


    var formatPatterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "HH:mm"
    )

    fun addFormatPattern(pattern: String) {
        formatPatterns = formatPatterns.toMutableList().apply { add(pattern) }
    }
}

typealias Country = String
typealias Language = String