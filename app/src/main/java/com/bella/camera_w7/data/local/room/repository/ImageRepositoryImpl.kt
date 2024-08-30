package com.bella.camera_w7.data.local.room.repository

import com.bella.camera_w7.data.local.room.ImageDao
import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.domain.repository.ImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao
) : ImageRepository {
    override suspend fun insertImage(image: ImageEntity) {
        imageDao.insertImage(image)
    }
    override suspend fun getAllImages(): List<ImageEntity> {
        return imageDao.getAllImages()
    }
}