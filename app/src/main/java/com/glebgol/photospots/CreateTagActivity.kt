package com.glebgol.photospots

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
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
    private val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var photoTaker: PhotoTaker
    private lateinit var locationClient: LocationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tag)

        photoTaker = PhotoTaker(this)
        photoTaker.file = intent.getStringExtra("file")?.let { File(it) }

        locationClient = LocationClient(this, locationCallback)

        titleInput = findViewById(R.id.titleInput)
        captureButton = findViewById(R.id.captureButton)
        submitButton = findViewById(R.id.submitButton)
        imageView = findViewById(R.id.imageView)

        imageURI = intent.getStringExtra("imageUri")
        imageView.setImageURI(Uri.parse(imageURI))

        captureButton.setOnClickListener {
            photoTaker.openCamera()
        }

        submitButton.setOnClickListener {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photoTaker.file!!)
            val imagePart = MultipartBody.Part.createFormData("image", photoTaker.file!!.name, requestFile)

            locationClient.getLastKnownLocation { location ->
                location?.let {
                    val call = ApiClient.tagApi
                        .createTag(imagePart, titleInput.text.toString(), location.longitude,
                            location.latitude)

                    call.enqueue(object : Callback<TagDetails> {
                        override fun onResponse(call: Call<TagDetails>, response: Response<TagDetails>) {
                            if (response.isSuccessful) {
                                val tag = response.body()
                                Log.i("Tagcreated","Tagcreated!: $tag")
                            } else {
                                Log.w("Error",
                                    "Error while creating tag: ${response.code()} - ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<TagDetails>, t: Throwable) {
                            Log.e("Failure when creating tag","Failure ${t.message}")
                        }
                    })
                }
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoTaker.photoUri?.let {
                imageView.setImageURI(it)
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

        }
    }
}
