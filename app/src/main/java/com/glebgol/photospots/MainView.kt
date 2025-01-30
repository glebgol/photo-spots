package com.glebgol.photospots

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import org.osmdroid.views.overlay.Marker

interface MainView {

    fun showMapWithTags(tags: List<Tag>)
    fun updateLocation(latitude: Double, longitude: Double)
    fun addMarker(tag: TagDetails)
    fun showTagDetails(marker: Marker, tag: TagDetails)
}
