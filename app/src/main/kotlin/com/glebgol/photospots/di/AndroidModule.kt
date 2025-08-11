package com.glebgol.photospots.di

import android.content.Context
import com.glebgol.photospots.presentation.DefaultLocationClient
import com.glebgol.photospots.presentation.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AndroidModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
}
