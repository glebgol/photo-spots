package com.glebgol.photospots.presenter

import com.glebgol.photospots.domain.data.Tag
import org.osmdroid.views.overlay.Marker

interface MainPresenter {

    fun loadMapWithTags()
    fun updateLocation()
    fun stop()
    fun loadTagDetails(marker: Marker, tag: Tag)
}
