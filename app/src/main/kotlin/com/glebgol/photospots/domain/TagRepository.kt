package com.glebgol.photospots.domain

interface TagRepository {
    suspend fun getAllTags(): List<TagData>
    suspend fun getTagDetails(tagId: String): TagDetailsData
    suspend fun updateTag(tagId: String, isFavourite: Boolean)
}
