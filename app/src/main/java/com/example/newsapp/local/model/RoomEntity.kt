package com.example.newsapp.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "News_Table")
data class RoomEntity(
		@PrimaryKey(autoGenerate = true) val id: Int,
		@ColumnInfo(name = "source_Name") var sourceName: String,
		@ColumnInfo(name = "author") var author: String,
		@ColumnInfo(name = "title") var title: String,
		@ColumnInfo(name = "publishedAt") var publishedAt: String,
		@ColumnInfo(name = "content") var content: String,
)