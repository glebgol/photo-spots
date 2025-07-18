package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto

interface TagsRemoteDataSource {
    suspend fun getTags(): List<TagDto>
    suspend fun getTagDetails(id: String): TagDetailsDto
}
