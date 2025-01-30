package com.glebgol.photospots.domain.osmdroid

import android.location.Location
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.compose.ui.node.getOrAddAdapter
import com.glebgol.photospots.R
import com.glebgol.photospots.TagDetailBottomSheetFragment
import com.glebgol.photospots.data.DefaultTagRepository
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import kotlinx.coroutines.runBlocking
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OSMDroidMapController(private val activity: AppCompatActivity, private val map: MapView) :
    MapController {

    private lateinit var mapController: IMapController

    override fun initMap() {
        loadOSMdroidConfiguration()
        val repository = DefaultTagRepository()

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        mapController = map.controller
        mapController.setZoom(7.0)
        map.minZoomLevel = 3.0
//        fetchTags()

        map.setVerticalMapRepetitionEnabled(false)
        map.setHorizontalMapRepetitionEnabled(false)

        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 20)
        map.setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 20)
    }

    private fun loadOSMdroidConfiguration() {
        Configuration.getInstance().load(
            activity.applicationContext,
            activity.getSharedPreferences(activity.getString(R.string.app_name), MODE_PRIVATE)
        )
    }

    private fun fetchTags() {
        val call = ApiClient.tagApi.getTags()


        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful && response.body() != null) {
                    displayTagsOnMap(response.body()!!)
                    Log.i("Tags", "Tags!: $response.body()!!")
                } else {
                    Log.w(
                        "Error",
                        "Error while getting tags: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<Tag>>, t: Throwable) {
                Log.e("Failure when getting tags", "Failure ${t.message}")
            }
        })
    }

    private fun displayTagsOnMap(tags: List<Tag>) {
        Log.i("tags", "tags$tags")
        tags.forEach { tag ->
            val marker = Marker(map)
            marker.position = GeoPoint(tag.latitude, tag.longitude)
            marker.setOnMarkerClickListener { _, _ ->
                onMarkerClick(marker, tag)
                true
            }
            map.overlays.add(marker)
        }
        map.invalidate()
    }

    override fun updateLocation(newLocation: Location) {
        mapController.setCenter(GeoPoint(newLocation.latitude, newLocation.longitude))
    }

    override fun addMarker(tag: TagDetails) {
        val marker = Marker(map)
        marker.position = GeoPoint(tag.latitude, tag.longitude)
        marker.setOnMarkerClickListener { _, _ ->
            onMarkerClick(marker, Tag(tag.id, tag.longitude, tag.latitude))
            true
        }
        map.overlays.add(marker)
        map.invalidate()
    }

    override fun showMap() {
        map.invalidate()
    }

    private fun fetchTagDetails(id: Long): TagDetails {
        TODO("Not yet implemented")
    }

    fun onMarkerClick(marker: Marker, tag: Tag) {
        val call = ApiClient.tagApi.getTagById(tag.id)
        call.enqueue(object : Callback<TagDetails> {
            override fun onResponse(call: Call<TagDetails>, response: Response<TagDetails>) {
                if (response.isSuccessful && response.body() != null) {
                    val tagDetails = response.body()!!

                    marker.title = tagDetails.description

                    val dialog = TagDetailBottomSheetFragment.newInstance(tagDetails)
                    dialog.show(activity.supportFragmentManager, "")

                    Log.i("Tags", "Tags!: ${response.body()}")
                } else {
                    Log.w(
                        "Error",
                        "Error while getting tag details: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<TagDetails>, t: Throwable) {
                Log.e("Failure when getting tags", "Failure ${t.message}")
            }
        })
    }
}
