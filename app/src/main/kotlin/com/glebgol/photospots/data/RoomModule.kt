package com.glebgol.photospots.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.glebgol.photospots.data.retrofit.RetrofitTagsRemoteDataSource
import com.glebgol.photospots.data.room.RoomTagsLocalDataSource
import com.glebgol.photospots.data.room.dao.TagsDao
import com.glebgol.photospots.data.room.database.TagsDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideTagsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TagsDatabase::class.java,
            "photo_spots_database"
        ).build()

    @Provides
    @Singleton
    fun provideTagsDao(tagsDatabase: TagsDatabase): TagsDao = tagsDatabase.tagsDao
}

@Module
@InstallIn(SingletonComponent::class)
interface Module2 {

    @Binds
    fun bindLocalDataSource(
        localDataSource: RoomTagsLocalDataSource
    ): TagsLocalDataSource

    @Binds
    fun bindRemoteDataSource(
        remoteDataSource: RetrofitTagsRemoteDataSource
    ): TagsRemoteDataSource
}