package com.glebgol.photospots.domain.data

import java.io.Serializable

data class TagDetails(
    val id: Long,
    val longitude: Double,
    val latitude: Double,
    val description: String,
    val imageUri: String
) : Serializable
