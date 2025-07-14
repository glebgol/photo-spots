package com.glebgol.photospots.domain

interface TagRepository {
    suspend fun getAllTags(): List<TagData>
}
