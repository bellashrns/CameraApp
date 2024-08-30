package com.bella.camera_w7.data.di

import com.bella.camera_w7.data.local.room.repository.ImageRepositoryImpl
import com.bella.camera_w7.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RoomModule::class])
@InstallIn(SingletonComponent::class)
abstract class RoomRepositoryModule {

    @Binds
    abstract fun provideImageRepository(
        roomRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}