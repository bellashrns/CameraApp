package com.bella.camera_w7.data.di

import android.content.Context
import androidx.room.Room
import com.bella.camera_w7.data.local.room.ImageDao
import com.bella.camera_w7.data.local.room.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(
            context,
            ImageDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideImageDao(database: ImageDatabase): ImageDao {
        return database.imageDao()
    }

    companion object {
        const val DATABASE_NAME = "gallery"
    }
}