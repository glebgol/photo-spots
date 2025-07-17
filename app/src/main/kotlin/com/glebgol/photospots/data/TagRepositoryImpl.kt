package com.glebgol.photospots.data

import com.glebgol.photospots.domain.TagData
import com.glebgol.photospots.domain.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagApi: TagApi
) : TagRepository {
    override suspend fun getAllTags(): List<TagData> {
        return tagApi.getTags().mapToData()
    }
}
