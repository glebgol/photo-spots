package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDto
import kotlinx.coroutines.flow.Flow

interface TagsLocalDataSource {
    suspend fun loadTags(): List<TagDto>
    suspend fun saveRemoteResponse(tags: List<TagDto>)
    suspend fun getTagById(id: String): TagDto
    fun getFavoriteTags(): Flow<List<TagDto>>
    suspend fun updateTag(id: String, isFavourite: Boolean)
}
