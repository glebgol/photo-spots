package com.glebgol.photospots.domain

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.glebgol.photospots.domain.google.GoogleMapController
import com.glebgol.photospots.domain.osmdroid.OSMDroidMapController
import com.google.android.gms.maps.GoogleMap
import org.osmdroid.views.MapView

class MapControllerFactory {

    fun createMapController(activity: AppCompatActivity, mapView: MapView): MapController {
        return OSMDroidMapController(activity, mapView)
    }

    fun createMapController(googleMap: GoogleMap): MapController {
        return GoogleMapController(googleMap)
    }
}
