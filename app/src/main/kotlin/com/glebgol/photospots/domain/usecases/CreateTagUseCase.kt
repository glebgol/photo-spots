package com.glebgol.photospots.domain.usecases

import android.graphics.Bitmap
import com.glebgol.photospots.domain.LocationClient
import com.glebgol.photospots.domain.TagRepository
import com.glebgol.photospots.domain.data.CreateTagData
import com.glebgol.photospots.domain.data.TagDetailsData
import javax.inject.Inject

class CreateTagUseCase @Inject constructor(
    private val tagRepository: TagRepository,
    private val locationClient: LocationClient
) {
    suspend fun execute(image: Bitmap, description: String): Result<TagDetailsData> {
        val location = locationClient.getLocation()

        return tagRepository.createTag(
            CreateTagData(
                image = image,
                description = description,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }
}
