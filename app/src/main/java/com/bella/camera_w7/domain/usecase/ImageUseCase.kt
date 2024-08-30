package com.bella.camera_w7.domain.usecase

import com.bella.camera_w7.data.local.room.entity.ImageEntity

interface ImageUseCase {
    suspend fun insertImage(image: ImageEntity)
    suspend fun getAllImages(): List<ImageEntity>
}