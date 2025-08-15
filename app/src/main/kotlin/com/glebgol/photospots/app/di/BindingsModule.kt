package com.glebgol.photospots.app.di

import com.glebgol.photospots.data.TagRepositoryImpl
import com.glebgol.photospots.data.TagsLocalDataSource
import com.glebgol.photospots.data.TagsRemoteDataSource
import com.glebgol.photospots.data.retrofit.RetrofitTagsRemoteDataSource
import com.glebgol.photospots.data.room.RoomTagsLocalDataSource
import com.glebgol.photospots.domain.TagRepository
import com.glebgol.photospots.data.DefaultLocationClient
import com.glebgol.photospots.domain.LocationClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindingsModule {

    @Binds
    fun bindTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository

    @Binds
    fun bindLocationClient(locationClient: DefaultLocationClient): LocationClient

    @Binds
    fun bindLocalDataSource(
        localDataSource: RoomTagsLocalDataSource
    ): TagsLocalDataSource

    @Binds
    fun bindRemoteDataSource(
        remoteDataSource: RetrofitTagsRemoteDataSource
    ): TagsRemoteDataSource
}
