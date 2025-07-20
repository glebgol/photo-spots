package com.glebgol.photospots.data.room

import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.TagsLocalDataSource
import com.glebgol.photospots.data.room.dao.TagsDao
import com.glebgol.photospots.data.toDto
import com.glebgol.photospots.data.toEntities
import com.glebgol.photospots.data.toEntity
import javax.inject.Inject

class RoomTagsLocalDataSource @Inject constructor(
    private val tagsDao: TagsDao
): TagsLocalDataSource {

    override suspend fun loadTags(): List<TagDto> {
        return tagsDao.selectAllTags()
            .toDto()
    }


    override suspend fun saveRemoteResponse(tags: List<TagDto>) {
        val existingTags = tagsDao.selectAllTags()
            .associateBy { it.id }

        val newTagEntities = tags.map { tagDto ->
            val existingTag = existingTags[tagDto.id]
            tagDto.toEntity().copy(
                isFavourite = existingTag?.isFavourite ?: 0
            )
        }

        tagsDao.upsertTags(tagEntities = newTagEntities)
    }

    override suspend fun getTagById(id: String): TagDto {
        return tagsDao.selectTagById(tagId = id)
            .toDto()
    }

    override suspend fun updateTag(id: String, isFavourite: Boolean) {
        tagsDao.updateTag(id = id, isFavourite = if (isFavourite) 1 else 0)
    }
}
