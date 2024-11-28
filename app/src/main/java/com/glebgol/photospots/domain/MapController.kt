package com.glebgol.photospots.domain

import android.location.Location
import org.osmdroid.util.GeoPoint

interface MapController {
    fun initMap()
    fun updateLocation(newLocation: Location)
    fun addMarker(title: String, position: GeoPoint)
    fun showMap()
}
