package com.glebgol.photospots.domain.osmdroid

import android.location.Location
import com.glebgol.photospots.domain.MapController
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OSMDroidMapController(private val map: MapView) : MapController {

    var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970)
    lateinit var mapController: IMapController

    override fun initMap() {
        mapController = map.controller

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        map.overlays.add(startMarker)
        mapController = map.controller
        mapController.setZoom(18.5)
        mapController.setCenter(startPoint)
    }

    override fun updateLocation(newLocation: Location) {
        mapController.setCenter(GeoPoint(newLocation.latitude, newLocation.longitude))
    }

    override fun addMarker(title: String, position: GeoPoint) {
        TODO("Not yet implemented")
    }

    override fun invalidateMap() {
        map.invalidate()
    }
}
