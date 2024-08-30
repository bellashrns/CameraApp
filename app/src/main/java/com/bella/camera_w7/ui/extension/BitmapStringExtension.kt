package com.bella.camera_w7.ui.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun String.ToBitmap(): Bitmap {
    val byteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Bitmap.ToByteString(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}