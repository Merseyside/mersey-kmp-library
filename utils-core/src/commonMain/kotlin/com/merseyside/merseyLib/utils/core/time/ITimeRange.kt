package com.merseyside.merseyLib.utils.core.time


interface ITimeRange {
    fun getStart(): TimeUnit
    fun getEnd(): TimeUnit

    fun getGap(): TimeUnit {
        return getEnd() - getStart()
    }
}
