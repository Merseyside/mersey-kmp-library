package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.*

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