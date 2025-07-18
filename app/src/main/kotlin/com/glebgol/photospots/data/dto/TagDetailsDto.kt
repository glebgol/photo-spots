package com.glebgol.photospots.data.dto

data class TagDetailsDto(
    val id: String,
    val imageUri: String,
    val description: String,
    val longitude: Double,
    val latitude: Double
)
