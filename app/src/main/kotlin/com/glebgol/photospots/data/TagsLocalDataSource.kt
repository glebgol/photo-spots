package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDto

interface TagsLocalDataSource {
    suspend fun loadTags(): List<TagDto>
    suspend fun saveRemoteResponse(tags: List<TagDto>)
    suspend fun getTagById(id: String): TagDto
    suspend fun updateTag(id: String, isFavourite: Boolean)
}
