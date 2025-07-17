package com.glebgol.photospots.domain

import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val tagRepository: TagRepository) {

    suspend fun getTags(): Result<List<TagData>> {
        try {
            return Result.success(tagRepository.getAllTags())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getTagsByQuery(query: String): List<TagData> {
        return tagRepository.getAllTags()
    }
}
