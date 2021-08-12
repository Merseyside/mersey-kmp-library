package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.Days
import com.merseyside.merseyLib.utils.core.time.getMonthRange
import com.merseyside.merseyLib.utils.core.time.minus
import com.merseyside.merseyLib.utils.core.time.ranges.MonthRange

fun MonthRange.getNextMonth(): MonthRange {
    return getMonthRange(getEnd())
}

fun MonthRange.getPrevMonth(): MonthRange {
    return getMonthRange(getStart() - Days(1))
}