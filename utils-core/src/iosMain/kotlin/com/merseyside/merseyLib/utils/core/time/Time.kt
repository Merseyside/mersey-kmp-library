package com.merseyside.merseyLib.utils.core.time

import platform.Foundation.*

actual fun getCurrentTimeMillis(timeZone: String): Long {
    val currentDate = NSDate() //now
    val dateFormatter = NSDateFormatter().apply {
        this.timeZone = NSTimeZone.create(timeZone) ?: throw IllegalArgumentException("Wrong timezone")
            .also { it.printStackTrace() }
    }

    val date = dateFormatter.dateFromString(dateFormatter.stringFromDate(currentDate))
        ?: throw Exception("Something goes wrong!")
    val newDouble = date.timeIntervalSince1970
    return newDouble.toLong() * 1000
}

actual fun getFormattedDate(timestamp: Long, pattern: String): String {
//    val date = NSDate.dateWithTimeIntervalSince1970(timestamp.toDouble() / 1000.0)
//
//    val dateFormatter = NSDateFormatter().run {
//        dateFormat = pattern
//        //timeZone = NSTimeZone.systemTimeZone
//    }
//
//    return dateFormatter.
    return ""
}