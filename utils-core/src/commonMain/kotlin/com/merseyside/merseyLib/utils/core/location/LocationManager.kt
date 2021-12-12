package com.merseyside.merseyLib.utils.core.location

import kotlinx.coroutines.flow.Flow

expect class LocationManager constructor() {

    suspend fun getLastLocation(): Location

    suspend fun getLocation(): Location

    fun getLocationFlow(): Flow<Location>

    fun hasRequestedPermissions(): Boolean
}