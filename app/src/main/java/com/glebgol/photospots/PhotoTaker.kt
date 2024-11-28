package com.glebgol.photospots

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class PhotoTaker(private val activity: ComponentActivity) {

    private val REQUEST_IMAGE_CAPTURE = 1

    var photoUri: Uri? = null
    var file: File? = null

    private val fileProviderAuthority = "com.glebgol.photospots.fileprovider"

    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            val photoFile: File? = createImageFile()
            file = photoFile
            photoFile?.also {
                photoUri = FileProvider.getUriForFile(
                    activity,
                    fileProviderAuthority,
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_$timeStamp", ".jpg", storageDir)
    }
}
