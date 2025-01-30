package com.glebgol.photospots

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.location.LocationClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CreateTagActivity : ComponentActivity() {

    private lateinit var imageView: ImageView
    private lateinit var titleInput: EditText
    private lateinit var captureButton: Button
    private lateinit var submitButton: Button

    private var imageURI: String? = null
    private var filePath: String? = null

    private lateinit var locationClient: LocationClient
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tag)

        locationClient = LocationClient(this)

        takePhotoLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    imageURI = data.getStringExtra("imageUri")
                    filePath = data.getStringExtra("filePath")
                    Toast.makeText(this, "Updated data: $data", Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
        }

        titleInput = findViewById(R.id.titleInput)
        captureButton = findViewById(R.id.captureButton)
        submitButton = findViewById(R.id.submitButton)
        imageView = findViewById(R.id.imageView)

        filePath = intent.getStringExtra("filePath")
        imageURI = intent.getStringExtra("imageUri")
//        imageView.setImageURI(Uri.parse(imageURI))
//        imageView.adjustViewBounds
        Glide.with(this)
            .load(imageURI)
            .into(imageView)
//        imageView.layoutParams = LinearLayout.LayoutParams(400, 400)
//        imageView.x = 20F //setting margin from left
//        imageView.y = 20F
        captureButton.setOnClickListener {
            takePhotoLauncher.launch(Intent(applicationContext, HighQualityPhotoTakerActivity::class.java))
        }

        submitButton.setOnClickListener {
            val file = filePath?.let { File(it) }

            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file!!)
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1)

            locationClient.getLastKnownLocation { location ->
                location?.let {
                    val call = ApiClient.tagApi
                        .createTag(
                            imagePart, titleInput.text.toString().trim(), location.longitude,
                            location.latitude
                        )

                    call.enqueue(object : Callback<TagDetails> {
                        override fun onResponse(
                            call: Call<TagDetails>,
                            response: Response<TagDetails>
                        ) {
                            if (response.isSuccessful) {
                                val tag = response.body()
                                Log.i("Tagcreated", "Tagcreated!: $tag")
                                val resultIntent = Intent()
                                resultIntent.putExtra("NEW_TAG", tag) // Pass the tag as an extra
                                setResult(RESULT_OK, resultIntent)
                                finish() // Finish the activity and return to MainActivity
                            } else {
                                Log.w(
                                    "Error",
                                    "Error while creating tag: ${response.code()} - ${response.message()}"
                                )
                            }
                        }

                        override fun onFailure(call: Call<TagDetails>, t: Throwable) {
                            Log.e("Failure when creating tag", "Failure ${t.message}")
                        }
                    })
                }
            }
        }
    }





    private fun updateUI() {
        imageView.setImageURI(Uri.parse(imageURI))
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

        }
    }
}
