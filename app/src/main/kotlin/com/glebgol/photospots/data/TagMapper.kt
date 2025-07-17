package com.glebgol.photospots.data

import com.glebgol.photospots.domain.TagData
import com.glebgol.photospots.domain.TagDetailsData

fun TagDto.mapToData(): TagData {
    return TagData(
        id = id,
        imageUrl = image,
        description = description,
        longitude = longitude,
        latitude = latitude,
    )
}

fun List<TagDto>.mapToData(): List<TagData> {
    return map { tag -> tag.mapToData() }
}

fun TagDetailsDto.mapToData(): TagDetailsData {
    return TagDetailsData(
        id = id,
        imageUrl = imageUri,
        description = description,
        longitude = longitude,
        latitude = latitude,
    )
}
