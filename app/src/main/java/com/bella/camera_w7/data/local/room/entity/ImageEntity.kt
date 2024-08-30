package com.bella.camera_w7.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "bitmap") val bitmap: String
)