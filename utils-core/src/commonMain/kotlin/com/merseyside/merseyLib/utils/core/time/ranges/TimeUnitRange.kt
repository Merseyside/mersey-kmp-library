package com.merseyside.merseyLib.utils.core.time.ranges

import com.merseyside.merseyLib.utils.core.time.TimeUnit

data class TimeUnitRange(
    val startTime: TimeUnit,
    val endTime: TimeUnit
): TimeRange {

    init {
        requireValid()
    }

    override fun getStart() = startTime
    override fun getEnd() = endTime
}
