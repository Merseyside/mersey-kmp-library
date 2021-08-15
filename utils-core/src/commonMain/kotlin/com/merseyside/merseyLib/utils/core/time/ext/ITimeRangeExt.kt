package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.time.*
import com.merseyside.merseyLib.utils.core.time.ranges.TimeRange
import com.merseyside.merseyLib.utils.core.time.ranges.TimeUnitRange

fun TimeRange.toTimeUnitRange(): TimeUnitRange {
    return TimeUnitRange(getStart(), getEnd())
}

fun <T : TimeRange> List<T>.findEdge(): TimeUnitRange {
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

fun <T : TimeRange> List<T>.findEdge(block: (TimeUnitRange) -> T): T {
    val range = findEdge()
    return block(range)
}

fun <T : TimeRange> T.toDaysOfWeek(): List<DayOfWeek> {
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

fun <T : TimeRange> List<T>.toDaysOfWeek(): List<DayOfWeek> {
    val range = findEdge()
    return range.toDaysOfWeek()
}

fun TimeRange.toDayRanges(): List<TimeRange> {
    var nextDay = getStart().getNextDay()
    val dayRanges = mutableListOf<TimeRange>()

    dayRanges.add(TimeUnitRange(getStart(), nextDay))

    while (nextDay < getEnd()) {
        val tempNextDay = nextDay.getNextDay()
        if (tempNextDay < getEnd()) {
            dayRanges.add(TimeUnitRange(nextDay, tempNextDay))
        } else {
            dayRanges.add(TimeUnitRange(nextDay, getEnd()))
        }

        nextDay = tempNextDay
    }

    return dayRanges
}

@Throws(IllegalArgumentException::class)
fun TimeRange.toHoursMinutesOfDay(): TimeUnitRange {
    return TimeUnitRange(getStart().toHoursMinutesOfDay(), getEnd().toHoursMinutesOfDay())
}

fun TimeRange.isIntersect(other: TimeRange): Boolean {
    return getStart() <= other.getEnd() && getStart() >= other.getStart() ||
            getEnd() > other.getStart() && getEnd() < other.getEnd() ||
            getStart() >= other.getStart() && getEnd() <= other.getEnd()
}

fun TimeRange.contains(other: TimeRange): Boolean {
    return getStart() <= other.getStart() && getEnd() >= other.getEnd()
}

fun TimeRange.contains(timeUnit: TimeUnit): Boolean {
    return getStart() <= timeUnit && getEnd() >= timeUnit
}

fun TimeRange.isIntersect(timeUnit: TimeUnit): Boolean {
    return getStart() <= timeUnit && getEnd() >= timeUnit
}

fun TimeRange.getGap(): TimeUnit {
    return getEnd() - getStart()
}

fun TimeRange.shift(timeUnit: TimeUnit): TimeRange {
    return TimeUnitRange(getStart() + timeUnit, getEnd() + timeUnit)
}

fun TimeRange.shiftBack(timeUnit: TimeUnit): TimeRange {
    return TimeUnitRange(getStart() - timeUnit, getEnd() - timeUnit)
}

fun <T : TimeRange> T.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = "",
    suffix: String = ""
): T {
    Logger.log(
        tag,
        "$prefix start = ${getStart().getHumanDate()} end = ${getEnd().getHumanDate()} $suffix"
    )
    return this
}

fun <T : TimeRange> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = ""
): List<T> {
    forEach { it.logHuman(tag, prefix) }
    return this
}