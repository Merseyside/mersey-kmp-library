package com.merseyside.merseyLib.utils.core.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Lat,
    val longitude: Lon
)