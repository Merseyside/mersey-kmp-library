package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.time.*
import com.merseyside.merseyLib.utils.core.time.getToday

fun ITimeRange.toTimeUnitRange(): TimeUnitRange {
    return TimeUnitRange(getStart(), getEnd())
}

fun <T : ITimeRange> List<T>.findEdge(): TimeUnitRange {
    if (isEmpty()) throw  IllegalArgumentException("List can not be empty!")
    val mutList = this.toMutableList()

    return if (size > 1) {

        val first = mutList.removeFirst()
        var min: TimeUnit = first.getStart()
        var max: TimeUnit = first.getEnd()

        mutList.forEach {
            it.getStart().let { if (it < min) min = it }
            it.getEnd().let { if (it > max) max = it }
        }

        TimeUnitRange(min, max)

    } else {
        TimeUnitRange(first().getStart(), first().getEnd())
    }
}

fun <T : ITimeRange> List<T>.findEdge(block: (TimeUnitRange) -> T): T {
    val range = findEdge()
    return block(range)
}

fun <T : ITimeRange> T.toDaysOfWeek(): List<DayOfWeek> {
    return if (getGap() > Weeks(1)) {
        DayOfWeek.values().toList()
    } else {
        val startDay = getStart().toDayOfWeek()
        val endDay = getEnd().toDayOfWeek()

        if (startDay == endDay) {
            if (getGap() < Days(1)) {
                return listOf(startDay)
            } else {
                DayOfWeek.values().toList()
            }
        } else {
            val list = DayOfWeek.values().toMutableList()
            if (endDay.index < startDay.index) {
                val indexRange = (endDay.index + 1 until startDay.index)
                list.filter { !indexRange.contains(it.index) }.toList()
            } else {
                val indexRange = (startDay.index..endDay.index)
                list.filter { indexRange.contains(it.index) }.toList()
            }
        }
    }
}

fun <T : ITimeRange> List<T>.toDaysOfWeek(): List<DayOfWeek> {
    val range = findEdge()
    return range.toDaysOfWeek()
}

fun ITimeRange.toHoursMinutesOfDay(): TimeUnitRange {
    return TimeUnitRange(getStart().toHoursMinutesOfDay(), getEnd().toHoursMinutesOfDay())
}

fun ITimeRange.isIntersect(other: ITimeRange): Boolean {
    return getStart() <= other.getEnd() && getStart() >= other.getStart() ||
            getEnd() > other.getStart() && getEnd() < other.getEnd() ||
            getStart() >= other.getStart() && getEnd() <= other.getEnd()
}

fun ITimeRange.contains(other: ITimeRange): Boolean {
    return getStart() <= other.getStart() && getEnd() >= other.getEnd()
}

fun ITimeRange.isIntersect(timeUnit: TimeUnit): Boolean {
    return getStart() <= timeUnit && getEnd() >= timeUnit
}

fun <T : ITimeRange> T.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = "",
    suffix: String = ""
): T {
    Logger.log(tag, "$prefix start = ${getStart().getHumanDate()} end = ${getEnd().getHumanDate()} $suffix")
    return this
}

fun <T : ITimeRange> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = ""
): List<T> {
    forEach { it.logHuman(tag, prefix) }
    return this
}