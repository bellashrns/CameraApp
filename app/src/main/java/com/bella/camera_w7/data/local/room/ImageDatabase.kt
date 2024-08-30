package com.bella.camera_w7.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bella.camera_w7.data.local.room.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
