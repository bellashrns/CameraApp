package com.bella.camera_w7.ui.pages.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bella.camera_w7.R
import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.domain.usecase.ImageUseCase
import com.bella.camera_w7.ui.core.BaseViewModel
import com.bella.camera_w7.ui.extension.ToBitmap
import com.bella.camera_w7.ui.extension.ToByteString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val imageUseCase: ImageUseCase
) : BaseViewModel() {

    suspend fun saveToDatabase(bitmap: Bitmap) {
        val imageEntity = ImageEntity(0, bitmap.ToByteString())
        imageUseCase.insertImage(imageEntity)
    }

    suspend fun getNewestImage(context: Context): Bitmap {
        val images = imageUseCase.getAllImages()

        val resources = context.resources
        val imageEmpty = BitmapFactory.decodeResource(resources, R.drawable.image_empty)

        return if (images.isNotEmpty()) {
            images.first().bitmap.ToBitmap()
        } else {
            imageEmpty
        }
    }
}