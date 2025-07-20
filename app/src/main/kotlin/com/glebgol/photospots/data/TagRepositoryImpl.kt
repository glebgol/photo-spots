package com.glebgol.photospots.data

import com.glebgol.photospots.domain.TagData
import com.glebgol.photospots.domain.TagDetailsData
import com.glebgol.photospots.domain.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagsLocalDataSource: TagsLocalDataSource,
    private val tagsRemoteDataSource: TagsRemoteDataSource,
) : TagRepository {

    override suspend fun getAllTags(): List<TagData> {
        return try {
            tagsRemoteDataSource.getTags().also {
                tagsLocalDataSource.saveRemoteResponse(it)
            }.mapToData()
        } catch (e: Exception) {
            return tagsLocalDataSource.loadTags()
                .mapToData()
        }
    }

    override suspend fun getTagDetails(tagId: String): TagDetailsData {
        val remoteDetails = tagsRemoteDataSource.getTagDetails(id = tagId)
            .mapToData()

        return remoteDetails.copy(
            isFavourite = tagsLocalDataSource.getTagById(id = tagId)
                .isFavourite
        )
    }

    override suspend fun updateTag(tagId: String, isFavourite: Boolean) {
        tagsLocalDataSource.updateTag(id = tagId, isFavourite = isFavourite)
    }
}
