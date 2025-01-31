package com.glebgol.photospots.view

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails

interface TagsMapView {

    fun showMapWithTags(tags: List<Tag>)
    fun updateLocation(latitude: Double, longitude: Double)
    fun addMarker(tag: TagDetails)
}
