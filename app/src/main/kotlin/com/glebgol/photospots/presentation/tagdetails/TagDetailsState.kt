package com.glebgol.photospots.presentation.tagdetails

import com.glebgol.photospots.domain.TagDetailsData

data class TagDetailsState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val tagDetails: TagDetailsData? = null,
    val isFavourite: Boolean = false
)
