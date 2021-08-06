package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.Logger.log as loggerLog

fun <T> T.log(tag: Any = Logger.TAG, prefix: Any? = null, suffix: Any? = null): T {
    val msg = "${prefix ?: ""} $this ${suffix ?: ""}"
    Logger.log(tag, msg)

    return this
}

inline fun <reified T> T.logMsg(
    tag: String = T::class.simpleName ?: Logger.TAG,
    message: Any? = null
): T {
    return this.also { loggerLog(tag, message) }
}