package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDto

interface TagsLocalDataSource {
    suspend fun loadTags(): List<TagDto>
    suspend fun saveRemoteResponse(tags: List<TagDto>)
}
