package com.glebgol.photospots.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glebgol.photospots.data.room.dao.TagEntity
import com.glebgol.photospots.data.room.dao.TagsDao

@Database(
    entities = [
        TagEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TagsDatabase: RoomDatabase() {
    abstract val tagsDao: TagsDao
}
