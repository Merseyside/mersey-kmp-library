package com.merseyside.merseyLib.utils.core.time

import platform.Foundation.*

actual fun getCurrentTimeMillis(): Long {
    return 0
}

actual fun getFormattedDate(timestamp: Long, pattern: String, timeZone: String): String {
    return ""
}

actual fun getHoursMinutes(
    timestamp: Long,
    pattern: String,
    timeZone: String
): String {

}

actual fun getDayOfWeek(timestamp: Long, timeZone: String): DayOfWeek {

}

actual fun getSecondsOfDay(timestamp: Long, timeZone: String): Seconds {

}

actual fun getMinutesOfDay(timestamp: Long, timeZone: String): Minutes {

}

actual fun getHoursOfDay(timestamp: Long, timeZone: String): Hours {

}

actual fun getDayOfMonth(timestamp: Long, timeZone: String): Days {

}
