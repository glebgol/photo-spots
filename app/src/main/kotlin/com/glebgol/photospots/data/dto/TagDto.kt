package com.glebgol.photospots.data.dto

import com.google.gson.annotations.SerializedName

data class TagDto(
    val id: String,
    @SerializedName("imageUri")
    val image: String,
    val description: String,
    val longitude: Double,
    val latitude: Double
)
