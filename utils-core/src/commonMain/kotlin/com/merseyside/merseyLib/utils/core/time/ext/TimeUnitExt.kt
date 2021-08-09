package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.time.*

fun TimeUnit.toFormattedDate(pattern: String = TimeConfiguration.formatPattern): FormattedDate {
    return getFormattedDate(this, pattern)
}

fun TimeUnit.toHoursMinutes(): FormattedDate {
    return getHoursMinutes(this)
}

fun TimeUnit.toSecondsOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getSecondsOfDay(millis, timeZone)
}

fun TimeUnit.toMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getMinutesOfDay(millis, timeZone)
}

fun TimeUnit.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getHoursOfDay(millis, timeZone)
}

fun TimeUnit.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getHoursMinutesOfDay(millis, timeZone)
}

fun TimeUnit.toDayOfWeek(timeZone: String = TimeConfiguration.timeZone): DayOfWeek {
    return getDayOfWeek(millis, timeZone)
}

fun TimeUnit.toDayOfMonth(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return getDayOfMonth(millis, timeZone)
}

fun TimeUnit.getHumanDate(): FormattedDate {
    return if (!isMoreThanDay()) toHoursMinutes()
    else toFormattedDate()
}

fun TimeUnit.isExpired(): Boolean {
    return getCurrentTimeUnit() - this > TimeUnit.getEmpty()
}

fun TimeUnit.isMoreThanDay(): Boolean {
    return Days(1) < this
}

fun TimeUnit.toDayTimeRange(): ITimeRange {
    val day = toDays().round()
    return TimeUnitRange(day, day + Days(1))
}

fun TimeUnit.getNextDay(): Days {
    var currentDay = toDays().round()
    return ++currentDay as Days
}

fun TimeUnit.getPrevDay(): Days {
    var currentDay = toDays().round()
    return --currentDay as Days
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