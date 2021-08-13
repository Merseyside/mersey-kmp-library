package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.Days
import com.merseyside.merseyLib.utils.core.time.getMonthRange
import com.merseyside.merseyLib.utils.core.time.minus
import com.merseyside.merseyLib.utils.core.time.plus
import com.merseyside.merseyLib.utils.core.time.ranges.ITimeRange
import com.merseyside.merseyLib.utils.core.time.ranges.MonthRange

fun MonthRange.getNextMonth(): MonthRange {
    return getMonthRange(getEnd())
}

fun MonthRange.getPrevMonth(): MonthRange {
    return getMonthRange(getStart() - Days(1))
}

fun MonthRange.getFirstDay(): ITimeRange {
    return getStart().toDayTimeRange()
}

fun MonthRange.getLastDay(): ITimeRange {
    return (getEnd() - Days(1)).toDayTimeRange()
}

fun MonthRange.getDay(number: Int): ITimeRange {
    val timeUnit = getStart() + Days(number - 1)
    return if (contains(timeUnit)) timeUnit.toDayTimeRange()
    else throw IllegalArgumentException("Month has only ${getMonth().days} days.")
}