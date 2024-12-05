package com.glebgol.photospots

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.glebgol.photospots.databinding.ActivityMainBinding
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.MapControllerFactory
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.location.LocationClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

class MainActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var mapController: MapController

    private val mapControllerFactory: MapControllerFactory = MapControllerFactory()
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var createTagLauncher: ActivityResultLauncher<Intent>

    private lateinit var locationClient: LocationClient
    private lateinit var photoTaker: PhotoTaker

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
//                mapController.updateLocation(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        map = binding.map
        mapController = mapControllerFactory.createMapController(this, map)
        mapController.initMap()

        locationClient = LocationClient(this, locationCallback)
        locationClient.startLocationUpdates()
        locationClient.getLastKnownLocation { location ->
            location?.let {
                mapController.updateLocation(it)
            }
        }

        val cameraBtn: Button = findViewById(R.id.cameraBtn)
        photoTaker = PhotoTaker(this)

        cameraBtn.setOnClickListener {
            photoTaker.openCamera()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            createTagLauncher.launch(Intent(this, CreateTagActivity::class.java).apply {
                putExtra("imageUri", photoTaker.photoUri.toString())
                putExtra("file", photoTaker.file?.path)
            })

            setResult(RESULT_OK, intent)
        }
    }
}
