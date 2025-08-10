package com.glebgol.photospots.presentation.createtag

data class CreateTagState(
    val isLoading: Boolean = false,
    val isCameraOpen: Boolean = true,
    val description: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val imageUri: String = "",
    val isSuccess: Boolean = false,
    val isFailure: Boolean = false
)
