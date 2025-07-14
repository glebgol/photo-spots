package com.glebgol.photospots.data

import com.glebgol.photospots.domain.TagData

fun TagDto.mapToData(): TagData {
    return TagData(
        imageUrl = image,
        description = description,
        longitude = longitude,
        latitude = latitude,
    )
}

fun List<TagDto>.mapToData(): List<TagData> {
    return map { tag -> tag.mapToData() }
}
