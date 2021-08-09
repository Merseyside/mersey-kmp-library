package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.time.ext.getHumanDate

interface ITimeRange : Comparable<ITimeRange> {
    fun getStart(): TimeUnit
    fun getEnd(): TimeUnit

    fun getGap(): TimeUnit {
        return getEnd() - getStart()
    }

    @Throws(IllegalArgumentException::class)
    fun requireValid() {
        if (getStart() > getEnd())
            throw IllegalArgumentException("Start value ${getStart().getHumanDate()} must be less than end value ${getEnd().getHumanDate()}")
    }

    override fun compareTo(other: ITimeRange): Int {
        return getStart().compareTo(other.getStart())
    }
}
