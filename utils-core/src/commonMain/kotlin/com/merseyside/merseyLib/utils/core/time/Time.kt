package com.merseyside.merseyLib.utils.core.time

enum class TimeZone { SYSTEM, GMT }

expect fun getCurrentTimeMillis(): Long

/**
 * If set return type to Millis
 */
fun getCurrentTimeUnit(): TimeUnit {
    return Millis(getCurrentTimeMillis())
}

fun getHoursMinutes(timestamp: TimeUnit): FormattedDate {
    return FormattedDate(getHoursMinutes(timestamp.millis, "HH:mm"))
}

fun getDate(timestamp: TimeUnit): FormattedDate {
    return getFormattedDate(timestamp, "dd.MM.YYYY")
}

fun getDateWithTime(timestamp: TimeUnit): FormattedDate {
    return getFormattedDate(timestamp, "dd-MM-YYYY hh:mm")
}

fun getFormattedDate(timestamp: TimeUnit, pattern: String): FormattedDate {
    return FormattedDate(getFormattedDate(timestamp.millis, pattern))
}

expect fun getHoursMinutes(timestamp: Long, pattern: String, timeZone: String = TimeConfiguration.timeZone): String

expect fun getFormattedDate(timestamp: Long, pattern: String, timeZone: String = TimeConfiguration.timeZone): String
