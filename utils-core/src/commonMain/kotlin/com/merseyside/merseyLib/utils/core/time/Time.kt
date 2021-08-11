package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.ext.log
import com.merseyside.merseyLib.utils.core.time.ext.getDayCount
import com.merseyside.merseyLib.utils.core.time.ext.toHoursMinutesOfDay
import com.merseyside.merseyLib.utils.core.time.ext.toTimeUnit

enum class TimeZone { SYSTEM, GMT }

expect fun getCurrentTimeMillis(): Long

/**
 * If set return type to Millis
 */
fun getCurrentTimeUnit(): TimeUnit {
    return Millis(getCurrentTimeMillis())
}

fun getCurrentDayTime(timeZone: String = TimeZone.SYSTEM.toString()): TimeUnit {
    return getCurrentTimeUnit().toHoursMinutesOfDay(timeZone)
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

fun getToday(): TimeUnit {
    return getCurrentTimeUnit().toDays().round()
}

fun getTodayRange(): ITimeRange = TimeUnitRange(getToday(), getToday() + Days(1))

fun getHoursMinutesOfDay(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getHoursOfDay(timestamp, timeZone) + getMinutesOfDay(timestamp, timeZone)
}

fun getEndOfDay(): TimeUnit = Days(1) - Minutes(1)

fun getCurrentWeekRange(): ITimeRange {
    val dayOfWeek = getDayOfWeek(getCurrentTimeMillis()).log(prefix = "day of week")
    val today = getToday().log(prefix = "today =")

    val monday = today - dayOfWeek.toTimeUnit().log(prefix = "monday = ")
    val sunday = monday + Days(7)

    return TimeUnitRange(monday, sunday)
}

fun getCurrentMonthRange(): ITimeRange {
    val currentTime = getCurrentTimeMillis()

    val today = getToday()
    val dayOfMonth = getDayOfMonth(currentTime)

    val month = getMonth(currentTime)

    val monthStart = today + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(currentTime))
    return TimeUnitRange(monthStart, monthEnd)
}

expect fun getDayOfMonth(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Days

expect fun getDayOfWeek(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): DayOfWeek

expect fun getDayOfWeekHuman(
    timestamp: Long,
    language: Language = TimeConfiguration.language,
    pattern: String = TimeConfiguration.dayOfWeekPattern,
    timeZone: String = TimeConfiguration.timeZone
): String

expect fun getHoursMinutes(timestamp: Long, pattern: String, timeZone: String = TimeConfiguration.timeZone): String

expect fun getFormattedDate(timestamp: Long, pattern: String, timeZone: String = TimeConfiguration.timeZone): String

expect fun getSecondsOfDay(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Seconds

expect fun getMinutesOfDay(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Minutes

expect fun getHoursOfDay(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Hours

expect fun getMonth(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Month

expect fun getYear(timestamp: Long, timeZone: String = TimeConfiguration.timeZone): Years
