package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.ext.toTimeUnit
import io.ktor.http.parsing.*

fun <T : Number> T.toMillis(): Millis {
    return Millis(this.toLong())
}

fun <T : Number> T.toSeconds(): Seconds {
    return Seconds(this.toLong())
}

fun <T : Number> T.toMinutes(): Minutes {
    return Minutes(this.toLong())
}

fun <T : Number> T.toHours(): Hours {
    return Hours(this.toLong())
}

fun <T : Number> T.toDays(): Days {
    return Days(this.toLong())
}

fun <T : CharSequence> T.toTimeUnit(): Millis {
    return this.toString().toLong().toMillis()
}

fun <T : CharSequence> T.toSeconds(): Seconds {
    return this.toString().toLong().toSeconds()
}

fun <T : CharSequence> T.toMinutes(): Minutes {
    return this.toString().toLong().toMinutes()
}

fun <T : CharSequence> T.toHours(): Hours {
    return this.toString().toLong().toHours()
}

fun <T : CharSequence> T.toDays(): Days {
    return this.toString().toLong().toDays()
}

fun TimeUnit.toFormattedDate(pattern: String): FormattedDate {
    return toFormattedDate(this, pattern)
}

fun FormattedDate.toTimeUnit(vararg pattern: String): TimeUnit {
    val patternsList: List<String> = if (pattern.isNotEmpty()) pattern.toList() else TimeConfiguration.formatPatterns

    patternsList.forEach {
        try {
            formattedDate.toTimeUnit(it)?.let { timestamp -> return timestamp.toMillis() }
        } catch (e: ParseException) {
            Logger.logErr(tag = "TimeUnit", msg = "$it is wrong pattern to format time")
        }
    }

    throw Exception("Can not format response time with suggested patterns!")
}