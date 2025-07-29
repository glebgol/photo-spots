package com.glebgol.photospots.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.glebgol.photospots.domain.MapNavigator
import javax.inject.Inject

class AndroidMapNavigator @Inject constructor(
    private val context: Context
): MapNavigator {
    override fun openMap(latitude: Double, longitude: Double) {
        val geoUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")

        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            Toast.makeText(context, "No map application available", Toast.LENGTH_SHORT).show()
        }
    }
}
