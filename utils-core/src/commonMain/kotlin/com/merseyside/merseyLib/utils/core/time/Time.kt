package com.merseyside.merseyLib.utils.core.time

enum class TimeZone { SYSTEM, GMT }

expect fun getCurrentTimeMillis(): Long

/**
 * If set return type to Millis
 */
fun getCurrentTimeUnit(): TimeUnit {
    return Millis(getCurrentTimeMillis())
}

fun getHoursMinutes(timestamp: Long): String {
    return toFormattedDate(timestamp, "hh:mm")
}

fun getHoursMinutes(timestamp: TimeUnit): String {
    return getHoursMinutes(timestamp.toMillisLong())
}

fun getDate(timestamp: Long): String {
    return toFormattedDate(timestamp, "dd.MM.YYYY")
}

fun getDate(timestamp: TimeUnit): String {
    return getDate(timestamp.toMillisLong())
}

fun getDateWithTime(timestamp: Long): String {
    return toFormattedDate(timestamp, "dd-MM-YYYY hh:mm")
}

fun getDateWithTime(timestamp: TimeUnit): String {
    return getDateWithTime(timestamp.toMillisLong())
}

fun toFormattedDate(timestamp: TimeUnit, pattern: String): FormattedDate {
    return FormattedDate(toFormattedDate(timestamp.toMillisLong(), pattern))
}

expect fun toFormattedDate(timestamp: Long, pattern: String, timeZone: String = TimeConfiguration.timeZone): String
