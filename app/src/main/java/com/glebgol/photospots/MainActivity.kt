package com.glebgol.photospots

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.glebgol.photospots.databinding.ActivityMainBinding
import com.glebgol.photospots.domain.MapController
import com.glebgol.photospots.domain.MapControllerFactory
import com.glebgol.photospots.domain.location.LocationClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

class MainActivity : ComponentActivity() {
    private lateinit var map: MapView
    private lateinit var mapController: MapController

    private val mapControllerFactory: MapControllerFactory = MapControllerFactory()
    private val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var locationClient: LocationClient
    private lateinit var photoTaker: PhotoTaker
    private lateinit var lastLocation: Location

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                updateUIWithLocation(location)
            }
        }
    }

    private fun updateUIWithLocation(location: Location?) {
        if (location != null) {
            mapController.updateLocation(location)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadOSMdroidConfiguration()

        map = binding.map
        mapController = mapControllerFactory.createMapController(map)
        mapController.initMap()

        locationClient = LocationClient(this, locationCallback)
        locationClient.startLocationUpdates()
        locationClient.getLastKnownLocation { location ->
            location?.let {
                updateUIWithLocation(it)
                lastLocation = location
            }
        }

        val cameraBtn: Button = findViewById(R.id.cameraBtn)
        photoTaker = PhotoTaker(this)

        cameraBtn.setOnClickListener {
            photoTaker.openCamera()
        }
    }

    private fun loadOSMdroidConfiguration() {
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
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

            val intent = Intent(this, CreateTagActivity::class.java).apply {
                putExtra("imageUri", photoTaker.photoUri.toString())
                putExtra("file", photoTaker.file?.path)
            }

            startActivity(intent)

            setResult(RESULT_OK, intent)
        }
    }
}
