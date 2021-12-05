package com.merseyside.merseyLib.utils.core.location

import com.merseyside.merseyLib.utils.core.location.mapper.toMppLocation
import com.merseyside.utils.location.LocationManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

actual class LocationManager actual constructor() {

    private lateinit var locationManager: LocationManager

    constructor(locationManager: LocationManager): this() {
        this.locationManager = locationManager
    }

    actual suspend fun getLastLocation(): Location {
        return locationManager.getLastLocation().toMppLocation()
    }

    actual suspend fun getLocation(): Location {
        return locationManager.getLocation().toMppLocation()
    }

    actual fun getLocationFlow(): Flow<Location> {
        return locationManager.getLocationFlow().map { it.toMppLocation() }
    }

    actual fun hasRequestedPermissions(): Boolean {
        return locationManager.hasRequestedPermissions()
    }
}