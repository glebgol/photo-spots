package com.glebgol.photospots.domain

class GetTagsUseCase(private val tagRepository: TagRepository) {

    suspend fun getTags(): List<TagData> {
        return tagRepository.getAllTags()
    }

    suspend fun getTagsByQuery(query: String): List<TagData> {
        return tagRepository.getAllTags()
    }
}
