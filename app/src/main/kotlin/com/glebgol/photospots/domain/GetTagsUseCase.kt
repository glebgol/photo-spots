package com.glebgol.photospots.domain

import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val tagRepository: TagRepository) {

    suspend fun getTags(): List<TagData> {
        return tagRepository.getAllTags()
    }

    suspend fun getTagsByQuery(query: String): List<TagData> {
        return tagRepository.getAllTags()
    }
}
