package com.glebgol.photospots.domain

import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val tagRepository: TagRepository) {

    suspend fun execute(): Result<List<TagData>> {
        try {
            return Result.success(tagRepository.getAllTags())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
