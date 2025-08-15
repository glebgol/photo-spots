package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.domain.data.CreateTagData

interface TagsRemoteDataSource {
    suspend fun getTags(): List<TagDto>
    suspend fun getTagDetails(id: String): TagDetailsDto
    suspend fun getTagsByQuery(query: String): List<TagDto>
    suspend fun createTag(tag: CreateTagData): TagDetailsDto
}
