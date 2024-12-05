package com.glebgol.photospots.domain

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.glebgol.photospots.R
import com.glebgol.photospots.domain.data.TagDetails
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow
import org.osmdroid.views.overlay.Marker

class CustomMarkerInfoWindow(layoutResId: Int, mapView: MapView, private val tagDetails: TagDetails) : MarkerInfoWindow(layoutResId, mapView) {
    private var imageView: ImageView? = null
    private var descriptionView: TextView? = null
    override fun onOpen(item: Any?) {
        super.onOpen(item)

        mMarkerRef = item as Marker

        // Ensure views are initialized
        if (mView == null) {
            Log.w("CustomMarkerInfoWindow", "Error: mView is null!")
            return
        }

        // Reference the views from mView
        if (imageView == null) {
            imageView = mView.findViewById(R.id.marker_image)
        }
        if (descriptionView == null) {
            descriptionView = mView.findViewById(R.id.marker_description)
        }

        // Handle image
        val image: Drawable? = mMarkerRef.getImage()
        if (image != null) {
            imageView?.setImageDrawable(image)
            imageView?.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView?.visibility = View.VISIBLE
            imageView?.setImageURI(Uri.parse(tagDetails.imageUri))
        } else {
            imageView?.visibility = View.GONE
        }

        // Set description text
        descriptionView?.text = mMarkerRef.title // Adjust as needed to get the description
    }
}
