package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.DayOfWeek
import com.merseyside.merseyLib.utils.core.time.Days
import com.merseyside.merseyLib.utils.core.time.TimeUnit

fun DayOfWeek.toTimeUnit(): TimeUnit {
    return Days(index)
}