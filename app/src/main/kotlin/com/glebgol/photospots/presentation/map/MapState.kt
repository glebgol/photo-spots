package com.glebgol.photospots.presentation.map

import com.glebgol.photospots.domain.data.TagData

data class MapState(
    val markers: List<TagData> = emptyList(),
    val isLoading: Boolean = true
)
