package com.glebgol.photospots

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.glebgol.photospots.databinding.ActivityMainBinding
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.presenter.impl.DefaultTagDetailsPresenter
import com.glebgol.photospots.presenter.impl.DefaultTagsMapPresenter
import com.glebgol.photospots.presenter.TagDetailsPresenter
import com.glebgol.photospots.presenter.TagsMapPresenter
import com.glebgol.photospots.view.TagDetailsView
import com.glebgol.photospots.view.TagsMapView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity(), TagsMapView, TagDetailsView {

    private lateinit var map: MapView

    private lateinit var createTagLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    private lateinit var tagsMapPresenter: TagsMapPresenter
    private lateinit var tagsDetailsPresenter: TagDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        map = binding.map

        //TODO think of what we can do with it
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            1)

        tagsMapPresenter = DefaultTagsMapPresenter(this, applicationContext)
        tagsMapPresenter.loadMapWithTags()
        tagsMapPresenter.updateLocation()

        tagsDetailsPresenter = DefaultTagDetailsPresenter(this)

        val cameraBtn: Button = findViewById(R.id.cameraBtn)

        cameraBtn.setOnClickListener {
            takePhotoLauncher.launch(Intent(this, HighQualityPhotoTakerActivity::class.java))
        }

        createTagLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val newTag = data.getSerializableExtra("NEW_TAG") as TagDetails?
                    newTag?.let {
                        addMarker(newTag)
                    }
                }
            }
        }

        takePhotoLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val imageUri = data.getStringExtra("imageUri")
                    val filePath = data.getStringExtra("filePath")

                    createTagLauncher.launch(Intent(this, CreateTagActivity::class.java).apply {
                        putExtra("imageUri", imageUri)
                        putExtra("filePath", filePath)
                    })
                }
            }
        }

    }

    override fun showMapWithTags(tags: List<Tag>) {
        initMap(map)
        addTagsOnMap(tags)
    }

    override fun showTagDetails(marker: Marker, tag: TagDetails) {
        marker.title = tag.description

        val dialog = TagDetailBottomSheetFragment(tag)
        dialog.show(supportFragmentManager, "")
    }

    override fun updateLocation(latitude: Double, longitude: Double) {
        map.controller.setCenter(GeoPoint(latitude, longitude))
    }

    override fun addMarker(tag: TagDetails) {
        val marker = Marker(map)
        marker.position = GeoPoint(tag.latitude, tag.longitude)
        marker.setOnMarkerClickListener { _, _ ->
            onTagClick(marker, Tag(tag.id, tag.longitude, tag.latitude))
        }
        map.overlays.add(marker)
        map.invalidate()
    }

    private fun onTagClick(marker: Marker, tag: Tag): Boolean {
        tagsDetailsPresenter.loadTagDetails(marker, tag)
        return true
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        tagsMapPresenter.stopLocationUpdates()
    }

    private fun addTagsOnMap(tags: List<Tag>) {
        tags.forEach { tag ->
            val marker = Marker(map)
            marker.position = GeoPoint(tag.latitude, tag.longitude)

            marker.setOnMarkerClickListener { _, _ ->
                onTagClick(marker, tag)
            }
            map.overlays.add(marker)
        }
        map.invalidate()
    }

    private fun initMap(map: MapView) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        map.controller.setZoom(7.0)
        map.minZoomLevel = 3.0

        map.setVerticalMapRepetitionEnabled(false)
        map.setHorizontalMapRepetitionEnabled(false)

        val tileSystem = MapView.getTileSystem()
        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude,
            tileSystem.minLatitude, 20)
        map.setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude,
            tileSystem.maxLongitude, 20)
    }

}
