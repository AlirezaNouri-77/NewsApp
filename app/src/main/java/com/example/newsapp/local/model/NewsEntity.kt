package com.example.newsapp.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "News_Table")
data class NewsEntity(
		@PrimaryKey(autoGenerate = true) val id: Int = 0,
		@ColumnInfo(name = "articleId") var articleId: String,
		@ColumnInfo(name = "title") var title: String,
		@ColumnInfo(name = "description") var description: String?,
		@ColumnInfo(name = "publishedAt") var publishedAt: String,
		@ColumnInfo(name = "content") var content: String,
		@ColumnInfo(name = "source") var source: String,
		@ColumnInfo(name = "image_url") var imageUrl: String?,
		@ColumnInfo(name = "link") var link: String,
)