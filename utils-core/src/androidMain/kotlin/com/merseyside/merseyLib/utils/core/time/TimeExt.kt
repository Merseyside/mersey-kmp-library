@file:JvmName("AndroidTimeExt")

package com.merseyside.merseyLib.utils.core.time

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone as SystemTimeZone

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}

actual fun toFormattedDate(timestamp: Long, pattern: String, timeZone: String): String {
    return try {

        val sdf = SimpleDateFormat(pattern, Locale.US)

        val netDate = Date(timestamp)
        if (timeZone != TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }
}