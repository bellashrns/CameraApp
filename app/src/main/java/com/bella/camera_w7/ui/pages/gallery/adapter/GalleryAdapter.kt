package com.bella.camera_w7.ui.pages.gallery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bella.camera_w7.data.local.room.entity.ImageEntity

class GalleryAdapter : ListAdapter<ImageEntity, GalleryViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ImageEntity>() {
            override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}