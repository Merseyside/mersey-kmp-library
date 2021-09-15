package com.merseyside.merseyLib.utils.core

import platform.Foundation.NSLog
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
actual object Logger {
    actual var isEnabled: Boolean = true
    actual var isDebug: Boolean = true

    actual fun log(tag: Any, msg: Any) {
        NSLog("%s: %s", adoptTag(tag), adoptMsg(msg))
    }

    actual fun log(msg: Any) {
        NSLog("%s: %s", TAG, adoptMsg(msg))
    }

    actual fun logErr(tag: Any, msg: Any) {
        NSLog("%s Error: %s", adoptTag(tag), adoptMsg(msg))
    }

    actual fun logErr(msg: Any) {
        NSLog("%s Error: %s", TAG, adoptMsg(msg))
    }

    actual fun logErr(tag: Any, throwable: Throwable) {
        NSLog("%s Error: %s", adoptTag(tag), throwable.message)
    }

    actual fun logErr(throwable: Throwable) {
        NSLog("%s Error: %s", TAG, throwable.message)
    }

    actual fun logInfo(tag: Any, msg: Any) {
        NSLog("%s Info: %s", adoptTag(tag), adoptMsg(msg))
    }

    actual fun logInfo(msg: Any) {
        NSLog("%s Info: %s", TAG, adoptMsg(msg))
    }

    actual fun logWtf(tag: Any, msg: Any) {
        NSLog("%s Wtf?: %s", adoptTag(tag), adoptMsg(msg))
    }

    actual fun logWtf(msg: Any) {
        NSLog("%s Wtf: %s", TAG, adoptMsg(msg))
    }

    private fun isLogging(): Boolean {
        return isEnabled && isDebug
    }

    internal actual fun adoptTag(tag: Any?): String {

        return if (tag != null) {
            val strTag = if (tag is String) {
                tag.toString()
            } else {
                tag::class.simpleName ?: "No name"
            }

            if (strTag.isEmpty()) {
                TAG
            } else {
                strTag
            }
        } else {
            TAG
        }
    }

    internal actual fun adoptMsg(msg: Any?): String {
        return when (msg) {
            null -> {
                "null"
            }

            is String -> {
                msg
            }

            is Collection<*> -> {
                if (msg.isEmpty()) {
                    "Empty collection"
                } else {
                    msg.toString()
                }
            }

            else -> {
                msg.toString()
            }
        }
    }

    actual val TAG: String
        get() = TODO("Not yet implemented")
}