package com.glebgol.photospots.data

import com.google.gson.annotations.SerializedName

data class TagDto(
    @SerializedName("imageUri")
    val image: String,
    val description: String,
    val longitude: Double,
    val latitude: Double
)
