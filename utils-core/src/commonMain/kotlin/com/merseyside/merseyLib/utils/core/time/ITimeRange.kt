package com.merseyside.merseyLib.utils.core.time

interface ITimeRange: Comparable<ITimeRange> {
    fun getStart(): TimeUnit
    fun getEnd(): TimeUnit

    fun getGap(): TimeUnit {
        return getEnd() - getStart()
    }

    fun requireValid() {
        if (getStart() > getEnd()) throw IllegalArgumentException("Start value ${getStart()} must be less than end value ${getEnd()}")
    }

    override fun compareTo(other: ITimeRange): Int {
        return getStart().compareTo(other.getStart())
    }
}
