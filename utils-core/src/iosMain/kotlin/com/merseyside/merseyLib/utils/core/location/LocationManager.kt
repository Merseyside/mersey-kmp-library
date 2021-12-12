package com.merseyside.merseyLib.utils.core.location

import kotlinx.coroutines.flow.Flow

actual class LocationManager actual constructor() {
    actual suspend fun getLastLocation(): Location {
        TODO("Not yet implemented")
    }

    actual suspend fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    actual fun getLocationFlow(): Flow<Location> {
        TODO("Not yet implemented")
    }

    actual fun hasRequestedPermissions(): Boolean {
        TODO("Not yet implemented")
    }
}