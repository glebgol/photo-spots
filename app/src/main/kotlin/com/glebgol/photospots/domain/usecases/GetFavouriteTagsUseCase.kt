package com.glebgol.photospots.domain.usecases

import com.glebgol.photospots.domain.TagRepository
import com.glebgol.photospots.domain.data.TagData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    fun execute(): Result<Flow<List<TagData>>> {
        try {
            return Result.success(tagRepository.getFavoriteTags())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
