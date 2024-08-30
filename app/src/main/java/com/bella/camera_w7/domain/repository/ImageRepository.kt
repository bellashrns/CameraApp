package com.bella.camera_w7.domain.repository

import com.bella.camera_w7.data.local.room.entity.ImageEntity

interface ImageRepository {
    suspend fun insertImage(image: ImageEntity)
    suspend fun getAllImages(): List<ImageEntity>
}