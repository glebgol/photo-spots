package com.glebgol.photospots.presentation

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>
    suspend fun getLocation(): Location
    class LocationException(message: String): Exception()
}


