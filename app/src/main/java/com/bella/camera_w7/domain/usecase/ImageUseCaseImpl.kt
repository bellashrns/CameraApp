package com.bella.camera_w7.domain.usecase

import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.domain.repository.ImageRepository
import javax.inject.Inject

class ImageUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : ImageUseCase{
    override suspend fun insertImage(image: ImageEntity) {
        repository.insertImage(image)
    }

    override suspend fun getAllImages(): List<ImageEntity> {
        return repository.getAllImages()
    }
}