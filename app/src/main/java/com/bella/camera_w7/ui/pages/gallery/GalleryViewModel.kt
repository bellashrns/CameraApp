package com.bella.camera_w7.ui.pages.gallery

import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.domain.usecase.ImageUseCase
import com.bella.camera_w7.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val imageUseCase: ImageUseCase
) : BaseViewModel() {

    suspend fun getImages(): List<ImageEntity> {
        return imageUseCase.getAllImages()
    }
}