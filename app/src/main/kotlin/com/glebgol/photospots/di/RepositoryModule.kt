package com.glebgol.photospots.di

import com.glebgol.photospots.data.TagRepositoryImpl
import com.glebgol.photospots.domain.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository
}
