package com.example.newsapp.local.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "News_Table")
data class RoomEntity(
		@PrimaryKey(autoGenerate = true) val id: Int,
		@ColumnInfo(name = "articleId") var articleId: String,
		@ColumnInfo(name = "title") var title: String,
		@ColumnInfo(name = "description") var description: String,
		@ColumnInfo(name = "publishedAt") var publishedAt: String,
		@ColumnInfo(name = "content") var content: String,
		@ColumnInfo(name = "image_url") var imageUrl: String,
		@ColumnInfo(name = "image_uri") var imageUri: String,
		@ColumnInfo(name = "link") var link: String,
		@ColumnInfo(name = "isRead") var isRead: Boolean,
)