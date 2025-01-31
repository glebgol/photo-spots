package com.glebgol.photospots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.glebgol.photospots.domain.data.TagDetails
import com.bumptech.glide.Glide
import com.glebgol.photospots.view.TagDetailsView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.osmdroid.views.overlay.Marker

class TagDetailBottomSheetFragment(private val tagDetails: TagDetails) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.marker_info_window, container, false)

        val imageView: ImageView = view.findViewById(R.id.marker_image)
        val descriptionView: TextView = view.findViewById(R.id.marker_description)

        Glide.with(this)
            .load(tagDetails.imageUri)
            .into(imageView)

        descriptionView.text = tagDetails.description

        return view
    }
}
