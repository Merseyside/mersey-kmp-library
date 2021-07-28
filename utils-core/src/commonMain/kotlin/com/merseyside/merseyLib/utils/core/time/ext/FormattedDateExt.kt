package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.*

fun FormattedDate.toSecondsOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toSecondsOfDay().toFormattedDate()
}

fun FormattedDate.toMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toMinutesOfDay(timeZone).toFormattedDate()
}

fun FormattedDate.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursOfDay(timeZone).toFormattedDate()
}

fun FormattedDate.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursMinutesOfDay(timeZone).toFormattedDate()
}
