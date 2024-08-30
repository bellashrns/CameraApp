package com.bella.camera_w7.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bella.camera_w7.data.local.room.entity.ImageEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM gallery ORDER BY id DESC")
    suspend fun getAllImages(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: ImageEntity)
}