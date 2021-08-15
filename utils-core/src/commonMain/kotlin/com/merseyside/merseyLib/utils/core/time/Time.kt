package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.time.ext.*
import com.merseyside.merseyLib.utils.core.time.ranges.TimeRange
import com.merseyside.merseyLib.utils.core.time.ranges.MonthRange
import com.merseyside.merseyLib.utils.core.time.ranges.TimeUnitRange


object Time {
    enum class TimeZone { SYSTEM, GMT }

    val now: TimeUnit
        get() {
            return getCurrentTimeMillis()
        }

    val today: TimeUnit
        get() {
            return now.toDays().round()
        }

    val todayRange: TimeRange
        get() {
            return TimeUnitRange(today, today + Days(1))
        }

    fun getCurrentDayTime(timeZone: String = TimeZone.SYSTEM.toString()): TimeUnit {
        return now.toHoursMinutesOfDay(timeZone)
    }

    fun getEndOfDay(): TimeUnit = Days(1) - Minutes(1)

    fun getCurrentWeekRange(): TimeRange {
        return now.getWeekRange()
    }

    fun getCurrentMonthRange(): MonthRange {
        return now.getMonthRange()
    }

    fun getCurrentYear(): Years {
        return getYear(now)
    }
}

expect fun getCurrentTimeMillis(): Millis

internal expect fun getDayOfMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Days

internal expect fun getDayOfWeek(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): DayOfWeek

internal expect fun getDayOfWeekHuman(
    timeUnit: TimeUnit,
    language: Language = TimeConfiguration.language,
    pattern: String = TimeConfiguration.dayOfWeekPattern,
    timeZone: String = TimeConfiguration.timeZone
): FormattedDate

internal expect fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: String,
    timeZone: String = TimeConfiguration.timeZone
): FormattedDate

internal expect fun getSecondsOfDay(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Seconds

internal expect fun getMinutesOfHour(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Minutes

internal expect fun getHoursOfDay(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Hours

internal expect fun getMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Month

internal expect fun getYear(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Years
