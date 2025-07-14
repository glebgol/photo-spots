package com.glebgol.photospots.presentation

import com.glebgol.photospots.domain.TagData

data class TagsState(
    val tags: List<TagData>? = null,
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val isError: Boolean = false
)
