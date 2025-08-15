package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.room.dao.TagEntity
import com.glebgol.photospots.domain.data.TagData
import com.glebgol.photospots.domain.data.TagDetailsData

fun TagDto.mapToData(): TagData {
    return TagData(
        id = id,
        imageUrl = image,
        description = description,
        longitude = longitude,
        latitude = latitude,
        isFavourite = isFavourite,
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
        isFavourite = isFavourite,
    )
}

fun TagEntity.toDto(): TagDto = TagDto(
    id = id, image = imageUrl, description = description, longitude = longitude,
    latitude = latitude,
    isFavourite = isFavourite == 1
)

fun List<TagEntity>.toDto(): List<TagDto> {
    return map { tag -> tag.toDto() }
}

fun TagDto.toEntity(): TagEntity = TagEntity(
    id = id, imageUrl = image, description = description, longitude = longitude,
    latitude = latitude, isFavourite = if (isFavourite) 1 else 0
)

fun List<TagDto>.toEntities(): List<TagEntity> {
    return map { tag -> tag.toEntity() }
}
