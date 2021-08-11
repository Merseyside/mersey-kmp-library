package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.*

fun Month.getDayCount(year: Years = TimeConfiguration.year): Days {
    return if (this != Month.FEBRUARY) {
        days
    } else {
        if (year.isLeap()) days + 1
        else days
    }
}