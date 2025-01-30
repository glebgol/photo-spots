package com.glebgol.photospots

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.glebgol.photospots.databinding.ActivityMainBinding
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.MapControllerFactory
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.location.LocationClient
import org.osmdroid.views.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var mapController: MapController

    private val mapControllerFactory: MapControllerFactory = MapControllerFactory()
    private lateinit var createTagLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    private lateinit var locationClient: LocationClient
    private lateinit var photoTaker: PhotoTaker

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

//        viewModel.geoTags.observe(this) {
//            addTagsOnMap(it)
//        }

        map = binding.map
        mapController = mapControllerFactory.createMapController(this, map)
        mapController.initMap()

        locationClient = LocationClient(this)
        locationClient.startLocationUpdates()
        locationClient.getLastKnownLocation { location ->
            location?.let {
                mapController.updateLocation(it)
            }
        }

        val cameraBtn: Button = findViewById(R.id.cameraBtn)
        photoTaker = PhotoTaker(this)

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
                        mapController.addMarker(newTag)
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


    private fun addTagsOnMap(it: List<Tag>?): List<Tag>? {
        TODO("Not yet implemented")
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
        locationClient.stopLocationUpdates()
    }

}
