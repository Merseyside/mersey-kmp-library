package com.merseyside.merseyLib.utils.core.time.ranges

import com.merseyside.merseyLib.utils.core.time.TimeUnit
import com.merseyside.merseyLib.utils.core.time.ext.getHumanDate
import com.merseyside.merseyLib.utils.core.time.minus

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
