package com.merseyside.merseyLib.utils.core.location.mapper

import android.location.Location
import com.merseyside.merseyLib.utils.core.location.Location as MppLocation

fun Location.toMppLocation(): MppLocation {
    return MppLocation(latitude, longitude)
}