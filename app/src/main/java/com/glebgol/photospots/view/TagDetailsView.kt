package com.glebgol.photospots.view

import com.glebgol.photospots.domain.data.TagDetails
import org.osmdroid.views.overlay.Marker

interface TagDetailsView {

    fun showTagDetails(marker: Marker, tag: TagDetails)
}
