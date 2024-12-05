package com.glebgol.photospots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.glebgol.photospots.domain.data.TagDetails
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TagDetailBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var tagDetails: TagDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.marker_info_window, container, false)

        // Get the details passed from the activity
        tagDetails = arguments?.getSerializable("TAG_DETAILS") as TagDetails

        val imageView: ImageView = view.findViewById(R.id.marker_image)
        val descriptionView: TextView = view.findViewById(R.id.marker_description)

        // Load the image using Glide
        Glide.with(this)
            .load(tagDetails.imageUri)
            .into(imageView)

        descriptionView.text = tagDetails.description

        return view
    }

    companion object {
        fun newInstance(tagDetails: TagDetails): TagDetailBottomSheetFragment {
            val fragment = TagDetailBottomSheetFragment()
            val args = Bundle()
            args.putSerializable("TAG_DETAILS", tagDetails)
            fragment.arguments = args
            return fragment
        }
    }
}
