package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.time.ext.getDayCount
import com.merseyside.merseyLib.utils.core.time.ext.toDayOfWeek
import com.merseyside.merseyLib.utils.core.time.ext.toHoursMinutesOfDay
import com.merseyside.merseyLib.utils.core.time.ext.toTimeUnit
import com.merseyside.merseyLib.utils.core.time.ranges.ITimeRange
import com.merseyside.merseyLib.utils.core.time.ranges.MonthRange
import com.merseyside.merseyLib.utils.core.time.ranges.TimeUnitRange

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
    return getWeekRange(getCurrentTimeUnit())
}

fun getWeekRange(timestamp: TimeUnit): ITimeRange {
    val dayOfWeek = timestamp.toDayOfWeek()
    val days = timestamp.toDays().round()

    val monday = days - dayOfWeek.toTimeUnit()
    val sunday = monday + Days(7)

    return TimeUnitRange(monday, sunday)
}

fun getCurrentMonthRange(): MonthRange {
    return getMonthRange(getCurrentTimeUnit())
}

fun getMonthRange(timestamp: TimeUnit): MonthRange {
    val days: Days = timestamp.toDays().round()
    val millis = timestamp.millis

    val dayOfMonth = getDayOfMonth(millis)

    val month = getMonth(millis)

    val monthStart = days + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(millis))
    return MonthRange(monthStart, monthEnd)
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
