package com.glebgol.photospots.domain.google

import android.location.Location
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.data.TagDetails
import com.google.android.gms.maps.GoogleMap
import org.osmdroid.util.GeoPoint

class GoogleMapController(private val googleMap: GoogleMap) : MapController {

    override fun initMap() {
        TODO("Not yet implemented")
    }

    override fun updateLocation(newLocation: Location) {
        TODO("Not yet implemented")
    }

    override fun addMarker(tag: TagDetails) {
        TODO("Not yet implemented")
    }

    override fun showMap() {
        TODO("Not yet implemented")
    }
}
