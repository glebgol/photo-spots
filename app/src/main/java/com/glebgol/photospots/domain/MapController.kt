package com.glebgol.photospots.domain

import android.location.Location
import com.glebgol.photospots.domain.data.TagDetails
import org.osmdroid.util.GeoPoint

interface MapController {
    fun initMap()
    fun updateLocation(newLocation: Location)
    fun addMarker(tag: TagDetails)
    fun showMap()
}
