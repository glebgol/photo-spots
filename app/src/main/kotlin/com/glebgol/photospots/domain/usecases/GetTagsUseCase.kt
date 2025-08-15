package com.glebgol.photospots.domain.usecases

import com.glebgol.photospots.domain.data.TagData
import com.glebgol.photospots.domain.TagRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun execute(query: String? = null): Result<List<TagData>> {
        try {
            query?.let {
                Result.success(tagRepository.getTagsByQuery(it))
            }
            return Result.success(tagRepository.getAllTags())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
