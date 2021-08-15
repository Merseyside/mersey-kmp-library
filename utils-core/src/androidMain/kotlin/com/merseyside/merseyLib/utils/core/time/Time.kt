@file:JvmName("AndroidTime")

package com.merseyside.merseyLib.utils.core.time

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone as SystemTimeZone

actual fun getCurrentTimeMillis(): Millis {
    return Millis(System.currentTimeMillis())
}

actual fun getSecondsOfDay(timeUnit: TimeUnit, timeZone: String): Seconds {
    return Seconds(getUnit(timeUnit, Calendar.SECOND, timeZone))
}

actual fun getMinutesOfHour(
    timeUnit: TimeUnit,
    timeZone: String
): Minutes {
    return Minutes(getUnit(timeUnit, Calendar.MINUTE, timeZone))
}

actual fun getHoursOfDay(
    timeUnit: TimeUnit,
    timeZone: String
): Hours {
    return Hours(getUnit(timeUnit, Calendar.HOUR_OF_DAY, timeZone))
}

actual fun getDayOfMonth(timeUnit: TimeUnit, timeZone: String): Days {
    return Days(getUnit(timeUnit, Calendar.DAY_OF_MONTH, timeZone))
}

actual fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: String,
    timeZone: String,
): FormattedDate {
    return try {
        val sdf = SimpleDateFormat(pattern, TimeConfiguration.getLocale())

        val netDate = Date(timeUnit.millis)

        if (timeZone != Time.TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        FormattedDate(sdf.format(netDate))
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }
}

actual fun getDayOfWeekHuman(
    timeUnit: TimeUnit,
    language: String,
    pattern: String,
    timeZone: String
): FormattedDate {
    val result = try {
        val sdf = SimpleDateFormat(pattern, Locale(language))

        val netDate = Date(timeUnit.millis)

        if (timeZone != Time.TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        FormattedDate(sdf.format(netDate))
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }

    return result
}

actual fun getDayOfWeek(timeUnit: TimeUnit, timeZone: String): DayOfWeek {
    val newIndex = getUnit(timeUnit, Calendar.DAY_OF_WEEK, timeZone).let { day ->
        if (day == 1) 6
        else day - 2

    }
    return DayOfWeek.getByIndex(newIndex)
}

actual fun getMonth(timeUnit: TimeUnit, timeZone: String): Month {
    return Month.getByIndex(getUnit(timeUnit, Calendar.MONTH, timeZone))
}

actual fun getYear(timeUnit: TimeUnit, timeZone: String): Years {
    return Years(getUnit(timeUnit, Calendar.YEAR, timeZone))
}

private fun getUnit(
    timeUnit: TimeUnit,
    unit: Int,
    timeZone: String = TimeConfiguration.timeZone
): Int {
    val calendar = Calendar.getInstance()
    calendar.time = Date(timeUnit.millis)

    if (timeZone != Time.TimeZone.SYSTEM.toString()) {
        calendar.timeZone = SystemTimeZone.getTimeZone(timeZone)
    }

    return calendar.get(unit)
}