@file:JvmName("AndroidStringExt")
package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.utils.core.time.Millis
import com.merseyside.merseyLib.utils.core.time.TimeUnit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.util.Base64

@Throws(ParseException::class)
actual fun String.toTimeUnit(dateFormat: String): TimeUnit? {
    return try {
        val date = SimpleDateFormat(dateFormat, Locale.US).apply {
            isLenient = false
            timeZone = TimeZone.getTimeZone("GMT")
        }.parse(this)

        if (date != null) {
            Millis(date.time)
        } else {
            throw KotlinNullPointerException("Date can not be parse within following format")
        }
    } catch (e: ParseException) {
        null
    }
}

actual fun String.encodeBase64(): String {
    return Base64.encodeToString(toByteArray(), Base64.DEFAULT)
}

actual fun String.decodeBase64(): String {
    return Base64.decode(this, Base64.DEFAULT).toString()
}