package com.merseyside.merseyLib.utils.core.time.ranges

import com.merseyside.merseyLib.utils.core.time.Month
import com.merseyside.merseyLib.utils.core.time.TimeUnit
import com.merseyside.merseyLib.utils.core.time.ext.getNextMonth
import com.merseyside.merseyLib.utils.core.time.ext.getPrevMonth
import com.merseyside.merseyLib.utils.core.time.ext.toMonth

class MonthRange internal constructor(
    private val start: TimeUnit,
    private val end: TimeUnit
): ITimeRange {

    override fun getStart() = start
    override fun getEnd() = end

    fun getMonth(): Month {
        return start.toMonth()
    }
}

operator fun MonthRange.plus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getNextMonth()
    return month
}

operator fun MonthRange.minus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getPrevMonth()
    return month
}

operator fun MonthRange.inc(): MonthRange = this.getNextMonth()
operator fun MonthRange.dec(): MonthRange = this.getPrevMonth()