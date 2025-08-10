package com.glebgol.photospots.domain

import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun getAllTags(): List<TagData>
    suspend fun getTagDetails(tagId: String): Result<TagDetailsData>
    fun getFavoriteTags(): Flow<List<TagData>>
    suspend fun updateTag(tagId: String, isFavourite: Boolean)
    suspend fun getTagsByQuery(query: String): List<TagData>
    suspend fun createTag(tagData: CreateTagData): Result<TagDetailsData>
}
