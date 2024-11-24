package com.glebgol.photospots.domain.google

import android.location.Location
import com.glebgol.photospots.domain.MapController
import com.google.android.gms.maps.GoogleMap
import org.osmdroid.util.GeoPoint

class GoogleMapController(private val googleMap: GoogleMap) : MapController {

    override fun initMap() {
        TODO("Not yet implemented")
    }

    override fun updateLocation(newLocation: Location) {
        TODO("Not yet implemented")
    }

    override fun addMarker(title: String, position: GeoPoint) {
        TODO("Not yet implemented")
    }

    override fun invalidateMap() {
        TODO("Not yet implemented")
    }
}
