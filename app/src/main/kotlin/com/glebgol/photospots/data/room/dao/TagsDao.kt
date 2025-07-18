package com.glebgol.photospots.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TagsDao {

    @Query("SELECT * FROM ${TagEntity.TABLE_NAME}")
    suspend fun selectAllTags(): List<TagEntity>

    @Upsert
    suspend fun upsertTags(tagEntities: List<TagEntity>)
}
