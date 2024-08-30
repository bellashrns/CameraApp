package com.bella.camera_w7.ui.pages.camera.util

import android.content.Context
import android.widget.Toast

fun createToast(context: Context, message: Int) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}