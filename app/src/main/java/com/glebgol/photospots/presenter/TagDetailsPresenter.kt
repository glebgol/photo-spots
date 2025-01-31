package com.glebgol.photospots.presenter

import com.glebgol.photospots.domain.data.Tag
import org.osmdroid.views.overlay.Marker

interface TagDetailsPresenter {

    fun loadTagDetails(marker: Marker, tag: Tag)
}
