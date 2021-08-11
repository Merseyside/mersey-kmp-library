package com.merseyside.merseyLib.utils.core.time

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

actual fun getDayOfWeekHuman(
    timestamp: Long,
    language: Language,
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

actual fun getMonth(timestamp: Long, timeZone: String): Month {

}

actual fun getYear(timestamp: Long, timeZone: String): Years {

}
