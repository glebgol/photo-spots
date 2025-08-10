package com.glebgol.photospots.data

import com.glebgol.photospots.domain.CreateTagData
import com.glebgol.photospots.domain.TagData
import com.glebgol.photospots.domain.TagDetailsData
import com.glebgol.photospots.domain.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getTagsByQuery(query: String): List<TagData> {
        return tagsRemoteDataSource.getTagsByQuery(query = query)
            .mapToData()
    }

    override suspend fun createTag(tagData: CreateTagData): Result<TagDetailsData> {
        try {
            return Result.success(tagsRemoteDataSource.createTag(tagData).mapToData())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getTagDetails(tagId: String): Result<TagDetailsData> {
        try {
            val remoteDetails = tagsRemoteDataSource.getTagDetails(id = tagId)
                .mapToData()

            return Result.success(remoteDetails.copy(
                isFavourite = tagsLocalDataSource.getTagById(id = tagId)
                    .isFavourite
            ))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getFavoriteTags(): Flow<List<TagData>> {
        return tagsLocalDataSource.getFavoriteTags()
            .map { it.mapToData() }
    }

    override suspend fun updateTag(tagId: String, isFavourite: Boolean) {
        tagsLocalDataSource.updateTag(id = tagId, isFavourite = isFavourite)
    }
}
