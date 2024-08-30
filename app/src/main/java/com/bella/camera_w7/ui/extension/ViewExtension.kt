package com.bella.camera_w7.ui.extension

import android.view.View

fun View.gone() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun View.visible() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) this.visibility =
        View.VISIBLE
}