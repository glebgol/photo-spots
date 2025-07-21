package com.glebgol.photospots.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val tagRepository: TagRepository) {

    suspend fun execute(): Result<List<TagData>> {
        try {
            return Result.success(tagRepository.getAllTags())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getFavouritesTags(): Flow<List<TagData>> {
        return tagRepository.getFavoriteTags()
    }
}
