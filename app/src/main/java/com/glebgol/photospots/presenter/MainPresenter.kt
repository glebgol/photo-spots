package com.glebgol.photospots.presenter

import com.glebgol.photospots.domain.data.Tag
import org.osmdroid.views.overlay.Marker

interface MainPresenter {

    fun loadMapWithTags()
    fun loadTagDetails(tag: Tag)
    fun updateLocation()
    fun stop()
    fun onMarkerClick(marker: Marker, tag: Tag)
}
