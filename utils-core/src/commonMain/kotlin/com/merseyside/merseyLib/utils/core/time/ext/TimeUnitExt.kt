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

fun TimeUnit.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): TimeUnit {
    Logger.log(tag, "$prefix = ${getHumanDate()}")
    return this
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