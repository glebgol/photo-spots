package com.glebgol.photospots.domain

import android.graphics.Bitmap

data class CreateTagData(
    val image: Bitmap,
    val description: String,
    val longitude: Double,
    val latitude: Double,
)
