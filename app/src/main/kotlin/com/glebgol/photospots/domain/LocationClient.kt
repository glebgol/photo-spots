package com.glebgol.photospots.domain

import com.glebgol.photospots.domain.data.LocationData
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<LocationData>
    suspend fun getLocation(): LocationData
    class LocationException(message: String): Exception()
}
