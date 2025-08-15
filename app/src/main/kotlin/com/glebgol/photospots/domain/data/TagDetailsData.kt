package com.glebgol.photospots.domain.data

data class TagDetailsData(
    val id: String,
    val imageUrl: String,
    val description: String,
    val longitude: Double,
    val latitude: Double,
    val isFavourite: Boolean
)
