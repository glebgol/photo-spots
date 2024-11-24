package com.glebgol.photospots.domain

import com.glebgol.photospots.domain.google.GoogleMapController
import com.glebgol.photospots.domain.osmdroid.OSMDroidMapController
import com.google.android.gms.maps.GoogleMap
import org.osmdroid.views.MapView

class MapControllerFactory {

    fun createMapController(mapView: MapView): MapController {
        return OSMDroidMapController(mapView)
    }

    fun createMapController(googleMap: GoogleMap): MapController {
        return GoogleMapController(googleMap)
    }
}
