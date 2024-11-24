package com.glebgol.photospots.domain.osmdroid

import android.location.Location
import android.util.Log
import android.widget.Toast
import com.glebgol.photospots.domain.ApiClient
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.data.Tag
import org.osmdroid.api.IGeoPoint
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


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

        fetchTags()
    }

    private fun fetchTags() {
        val call = ApiClient.tagApi.getTags()
        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful && response.body() != null) {
                    displayTagsOnMap(response.body()!!)
                    Log.w("Victory", "Victory")
                } else {
                    Log.w("Empty1", "Empty1")
                }
            }

            override fun onFailure(call: Call<List<Tag>>, t: Throwable) {
                Log.w("Empty2", "Empty2")

            }
        })
    }

    private fun displayTagsOnMap(tags: List<Tag>) {
//        val points: List<IGeoPoint> = tags.map { tag -> LabelledGeoPoint(tag.latitude, tag.longitude) }
//
//        val pt = SimplePointTheme(points, true)
//
//        val opt = SimpleFastPointOverlayOptions.getDefaultStyle()
//            .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
//            .setRadius(7.0F).setIsClickable(true).setCellSize(15)
//
//        val sfpo = SimpleFastPointOverlay(pt, opt)
//
//        map.overlays.add(sfpo);

        Log.i("tags", "tags$tags")
        tags.forEach { tag ->
            val startMarker = Marker(map)
            startMarker.position = GeoPoint(tag.latitude, tag.longitude)
//            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            map.overlays.add(startMarker)
        }
        map.invalidate()

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
