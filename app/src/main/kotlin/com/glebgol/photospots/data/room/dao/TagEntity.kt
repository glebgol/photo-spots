package com.glebgol.photospots.data.room.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glebgol.photospots.data.room.dao.TagEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TagEntity(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val description: String,
    val longitude: Double,
    val latitude: Double
) {
    companion object {
        const val TABLE_NAME = "tags"
    }
}
