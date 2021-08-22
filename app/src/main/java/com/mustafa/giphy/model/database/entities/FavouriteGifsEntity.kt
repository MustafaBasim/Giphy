package com.mustafa.giphy.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite_gifs_table")
data class FavouriteGifsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id", index = true)
    var id: String,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "created_at")
    var createdAt: Long?,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "original_url")
    var originalUrl: String?,

    @ColumnInfo(name = "preview_url")
    var previewUrl: String?
)
