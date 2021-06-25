package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.utils.core.time.Millis
import com.merseyside.merseyLib.utils.core.time.TimeUnit

actual fun String.toTimeUnit(dateFormat: String): TimeUnit? {
    return Millis(0)
}