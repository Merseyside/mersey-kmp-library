@file:JvmName("AndroidTime")

package com.merseyside.merseyLib.utils.core.time

import android.os.Build
import com.merseyside.merseyLib.utils.core.ext.log
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.TimeZone as SystemTimeZone

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}

actual fun getSecondsOfDay(timestamp: Long, timeZone: String): Seconds {
    return Seconds(getUnitOfDay(timestamp, Calendar.SECOND, timeZone))
}

actual fun getMinutesOfDay(
    timestamp: Long,
    timeZone: String
): Minutes {
    return Minutes(getUnitOfDay(timestamp, Calendar.MINUTE, timeZone))
}

actual fun getHoursOfDay(
    timestamp: Long,
    timeZone: String
): Hours {
    return Hours(getUnitOfDay(timestamp, Calendar.HOUR_OF_DAY, timeZone))
}

actual fun getDayOfMonth(timestamp: Long, timeZone: String): Days {
    return Days(getUnitOfDay(timestamp, Calendar.DAY_OF_MONTH, timeZone))
}

actual fun getFormattedDate(
    timestamp: Long,
    pattern: String,
    timeZone: String,
): String {
    return try {
        val sdf = SimpleDateFormat(pattern, TimeConfiguration.getLocale())

        val netDate = Date(timestamp)

        if (timeZone != TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }
}

actual fun getHoursMinutes(
    timestamp: Long,
    pattern: String,
    timeZone: String
): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            val newTime = if (Days(1).millis <= timestamp) getHoursMinutesOfDay(
                timestamp,
                timeZone
            ).millis else timestamp

            val localTime = LocalTime.ofSecondOfDay(newTime / Conversions.MILLIS_CONST)

            val formatterUS = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(TimeConfiguration.getLocale())
            localTime.format(formatterUS)
        } catch (e: DateTimeException) {
            e.printStackTrace()
            "null"
        }
    } else {
        getFormattedDate(timestamp, pattern, timeZone)
    }
}


actual fun getDayOfWeekHuman(
    timestamp: Long,
    language: String,
    pattern: String,
    timeZone: String
): String {
    val result = try {
        val sdf = SimpleDateFormat(pattern, Locale(language))

        val netDate = Date(timestamp)

        if (timeZone != TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }

    return result
}

actual fun getDayOfWeek(timestamp: Long, timeZone: String): DayOfWeek {
    val human = getDayOfWeekHuman(timestamp, "en", "EEEE", timeZone)
    return DayOfWeek.valueOf(human.uppercase())
}

private fun getUnitOfDay(
    timestamp: Long,
    unit: Int,
    timeZone: String = TimeConfiguration.timeZone
): Int {
    val calendar = Calendar.getInstance()
    calendar.time = Date(timestamp)

    if (timeZone != TimeZone.SYSTEM.toString()) {
        calendar.timeZone = SystemTimeZone.getTimeZone(timeZone)
    }

    return calendar.get(unit)
}