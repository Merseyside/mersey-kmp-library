package com.merseyside.merseyLib.utils.core.location

import kotlinx.coroutines.flow.Flow

expect class LocationManager constructor() {

    /**
     * @return last cached [Location]
     */
    suspend fun getLastLocation(): Location

    /**
     * @return current [Location]
     */
    suspend fun getLocation(): Location

    /**
     * Observing location changing in real time
     */
    fun getLocationFlow(): Flow<Location>

    /**
     * @return true if all required system permissions granted, false otherwise
     */
    fun hasRequestedPermissions(): Boolean
}