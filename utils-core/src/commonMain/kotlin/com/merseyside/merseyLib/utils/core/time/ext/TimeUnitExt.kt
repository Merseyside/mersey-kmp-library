package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.time.*
import com.merseyside.merseyLib.utils.core.time.ranges.TimeRange
import com.merseyside.merseyLib.utils.core.time.ranges.MonthRange
import com.merseyside.merseyLib.utils.core.time.ranges.TimeUnitRange

fun TimeUnit.toFormattedDate(pattern: String = TimeConfiguration.defaultPattern): FormattedDate {
    return getFormattedDate(this, pattern)
}

fun TimeUnit.toSecondsOfDay(timeZone: String = TimeConfiguration.timeZone): Seconds {
    return getSecondsOfDay(this, timeZone)
}

fun TimeUnit.toMinutesOfHour(timeZone: String = TimeConfiguration.timeZone): Minutes {
    return getMinutesOfHour(this, timeZone)
}

fun TimeUnit.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): Hours {
    return getHoursOfDay(this, timeZone)
}

fun TimeUnit.getDate(): FormattedDate {
    return getFormattedDate(this, "dd.MM.YYYY")
}

fun TimeUnit.getDateWithTime(): FormattedDate {
    return getFormattedDate(this, "dd-MM-YYYY hh:mm")
}

fun TimeUnit.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return (getHoursOfDay(this, timeZone) + getMinutesOfHour(this, timeZone))
}

fun TimeUnit.toFormattedHoursMinutesOfDay(
    pattern: String = TimeConfiguration.hoursMinutesPattern,
    timeZone: String = TimeConfiguration.timeZone
): FormattedDate {
    return toHoursMinutesOfDay(timeZone).toFormattedDate(pattern)
}

fun TimeUnit.toDayOfWeek(timeZone: String = TimeConfiguration.timeZone): DayOfWeek {
    return getDayOfWeek(this, timeZone)
}

fun TimeUnit.toDayOfMonth(timeZone: String = TimeConfiguration.timeZone): Days {
    return getDayOfMonth(this, timeZone)
}

fun TimeUnit.getHumanDate(pattern: String = TimeConfiguration.defaultPattern): FormattedDate {
    return if (!isMoreThanDay()) toHoursMinutesOfDay().toFormattedDate(pattern)
    else toFormattedDate(pattern)
}

fun TimeUnit.isExpired(): Boolean {
    return Time.now - this > TimeUnit.getEmpty()
}

fun TimeUnit.isMoreThanDay(): Boolean {
    return Days(1) < this
}

fun TimeUnit.toDayTimeRange(): TimeRange {
    val day = toDays().round()
    return TimeUnitRange(day, day + Days(1))
}

fun TimeUnit.toTimeRange(startShift: TimeUnit): TimeRange {
    return TimeUnitRange(this, this + startShift)
}

fun TimeUnit.getNextDay(): Days {
    var currentDay = toDays().round()
    return ++currentDay
}

fun TimeUnit.getPrevDay(): Days {
    var currentDay = toDays().round()
    return --currentDay
}

fun TimeUnit.getWeekRange(): TimeRange {
    val dayOfWeek = toDayOfWeek()
    val days = toDays().round()

    val monday = days - dayOfWeek.toTimeUnit()
    val sunday = monday + Days(7)

    return TimeUnitRange(monday, sunday)
}

fun TimeUnit.toMonth(): Month {
    return getMonth(this)
}

fun TimeUnit.getMonthRange(): MonthRange {
    val days: Days = toDays().round()

    val dayOfMonth = getDayOfMonth(this)

    val month = getMonth(this)

    val monthStart = days + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(this))
    return MonthRange(monthStart, monthEnd)
}

fun <T : TimeUnit> T.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): T {
    Logger.log(tag, "$prefix ${getHumanDate()}")
    return this
}

fun <T : TimeUnit> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): List<T> {
    Logger.log(tag, "$prefix ${joinToString(separator = ", ") { it.getHumanDate().value }}")
    return this
}