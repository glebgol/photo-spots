package com.glebgol.photospots.data

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.repository.TagRepository
import okhttp3.MultipartBody

class DefaultTagRepository : TagRepository {

    override suspend fun getTags(): List<Tag> {
        TODO("Not yet implemented")
    }

    override suspend fun getTagById(id: Long): TagDetails {
        TODO("Not yet implemented")
    }

    override suspend fun createTag(
        image: MultipartBody.Part,
        description: String,
        longitude: Double,
        latitude: Double
    ): TagDetails {
        TODO("Not yet implemented")
    }
}