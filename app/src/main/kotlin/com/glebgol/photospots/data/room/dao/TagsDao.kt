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

    @Query("SELECT * FROM ${TagEntity.TABLE_NAME} WHERE id = :tagId")
    suspend fun selectTagById(tagId: String): TagEntity

    @Query("UPDATE ${TagEntity.TABLE_NAME} SET isFavourite = :isFavourite WHERE id=:id")
    suspend fun updateTag(id: String, isFavourite: Int)
}
