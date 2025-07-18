package com.glebgol.photospots.data.room

import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.TagsLocalDataSource
import com.glebgol.photospots.data.room.dao.TagsDao
import com.glebgol.photospots.data.toDto
import com.glebgol.photospots.data.toEntities
import javax.inject.Inject

class RoomTagsLocalDataSource @Inject constructor(
    private val tagsDao: TagsDao
): TagsLocalDataSource {

    override suspend fun loadTags(): List<TagDto> {
        return tagsDao.selectAllTags()
            .toDto()
    }

    override suspend fun saveRemoteResponse(tags: List<TagDto>) {
        tagsDao.upsertTags(
            tagEntities = tags.toEntities()
        )
    }
}
