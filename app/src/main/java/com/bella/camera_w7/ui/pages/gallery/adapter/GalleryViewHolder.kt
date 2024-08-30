package com.bella.camera_w7.ui.pages.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.databinding.ItemImageBinding
import com.bella.camera_w7.ui.extension.ToBitmap

class GalleryViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageEntity?) {
        val imgBitmap = item?.bitmap?.ToBitmap()
        binding.ivImageItem.setImageBitmap(imgBitmap)
    }

    companion object {
        fun create(view: ViewGroup): GalleryViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = ItemImageBinding.inflate(inflater, view, false)
            return GalleryViewHolder(binding)
        }
    }
}