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
import com.glebgol.photospots.presenter.DefaultMainPresenter
import com.glebgol.photospots.presenter.MainPresenter
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var map: MapView

    private lateinit var createTagLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        map = binding.map

        //TODO think of what we can do with it
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            1)

        mainPresenter = DefaultMainPresenter(this, applicationContext)
        mainPresenter.loadMapWithTags()
        mainPresenter.updateLocation()

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

    private fun loadOSMdroidConfiguration() {
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
    }

    private fun addTagsOnMap(tags: List<Tag>) {
        tags.forEach { tag ->
            val marker = Marker(map)
            marker.position = GeoPoint(tag.latitude, tag.longitude)

            marker.setOnMarkerClickListener { _, _ ->
                mainPresenter.loadTagDetails(marker, tag)
                true
            }
            map.overlays.add(marker)
        }
        map.invalidate()
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
        mainPresenter.stop()
    }

    override fun showMapWithTags(tags: List<Tag>) {
        loadOSMdroidConfiguration()

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        map.controller.setZoom(7.0)
        map.minZoomLevel = 3.0

        map.setVerticalMapRepetitionEnabled(false)
        map.setHorizontalMapRepetitionEnabled(false)

        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 20)
        map.setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 20)

        addTagsOnMap(tags)
    }

    override fun showTagDetails(marker: Marker, tag: TagDetails) {
        marker.title = tag.description

        val dialog = TagDetailBottomSheetFragment.newInstance(tag)
        dialog.show(supportFragmentManager, "")
    }

    override fun updateLocation(latitude: Double, longitude: Double) {
        map.controller.setCenter(GeoPoint(latitude, longitude))
    }

    override fun addMarker(tag: TagDetails) {
        val marker = Marker(map)
        marker.position = GeoPoint(tag.latitude, tag.longitude)
        marker.setOnMarkerClickListener { _, _ ->
            mainPresenter.loadTagDetails(marker, Tag(tag.id, tag.longitude, tag.latitude))
            true
        }
        map.overlays.add(marker)
        map.invalidate()
    }

}
