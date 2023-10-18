package com.example.newsapp.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.local.dao.RoomDao
import com.example.newsapp.local.model.RoomEntity

@Database(entities = [RoomEntity::class], version = 3)
abstract class NewsRoomDatabase : RoomDatabase() {

		abstract fun RoomDao(): RoomDao

}