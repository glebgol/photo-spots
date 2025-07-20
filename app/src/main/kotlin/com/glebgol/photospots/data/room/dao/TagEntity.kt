package com.glebgol.photospots.data.room.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.glebgol.photospots.data.room.dao.TagEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(value = ["isFavourite"]),
        Index(value = ["id"])
    ]
)
data class TagEntity(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val description: String,
    val longitude: Double,
    val latitude: Double,
    @ColumnInfo(name = "isFavourite", defaultValue = "0")
    val isFavourite: Int
) {
    companion object {
        const val TABLE_NAME = "tags"
    }
}
