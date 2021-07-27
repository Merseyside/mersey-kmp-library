@file:JvmName("AndroidTime")

package com.merseyside.merseyLib.utils.core.time

import android.os.Build
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.TimeZone as SystemTimeZone

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
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
            val localTime = LocalTime.ofSecondOfDay(timestamp / Conversions.MILLIS_CONST)

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


actual fun getDayOfWeek(timestamp: Long, timeZone: String): DayOfWeek {
    //val localeDate = LocalDate.ofEpochDay(timestamp)
    val result = try {
        val sdf = SimpleDateFormat("EEEE", Locale("en"))

        val netDate = Date(timestamp)

        if (timeZone != TimeZone.SYSTEM.toString()) {
            sdf.timeZone = SystemTimeZone.getTimeZone(timeZone)
        }
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalArgumentException("Can not format date!")
    }

    return DayOfWeek.valueOf(result.uppercase())
}