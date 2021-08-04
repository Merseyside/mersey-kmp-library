package com.merseyside.merseyLib.utils.core.time

data class TimeUnitRange(
    val startTime: TimeUnit,
    val endTime: TimeUnit
): ITimeRange {

    init {
        requireValid()
    }

    override fun getStart() = startTime
    override fun getEnd() = endTime
}
