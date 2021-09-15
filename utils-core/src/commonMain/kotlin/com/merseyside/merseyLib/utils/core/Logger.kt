package com.merseyside.merseyLib.utils.core

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
expect object Logger {

    var isEnabled: Boolean
    var isDebug: Boolean

    fun log(tag: Any?, msg: Any? = "Empty msg")
    fun log(msg: Any? = "Empty msg")
    fun logErr(tag: Any?, msg: Any? = "Empty error")
    fun logErr(msg: Any? = "Empty error")
    fun logInfo(tag: Any?, msg: Any?)
    fun logInfo(msg: Any?)
    fun logWtf(tag: Any?, msg: Any? = "wtf?")
    fun logWtf(msg: Any? = "wtf?")
    fun logErr(tag: Any?, throwable: Throwable)
    fun logErr(throwable: Throwable)

    internal fun adoptTag(tag: Any?): String
    internal fun adoptMsg(msg: Any?): String

    val TAG: String
}