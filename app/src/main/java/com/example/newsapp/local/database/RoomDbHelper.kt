package com.example.newsapp.local.database

import android.content.Context
import androidx.room.Room

object RoomDbHelper {

		@Volatile
		private var INSTANCE: NewsRoomDatabase? = null

		fun getInstance(application: Context): NewsRoomDatabase {
				synchronized(this) {
						var instance = INSTANCE
						if (instance == null) {
								instance = Room.databaseBuilder(application, NewsRoomDatabase::class.java, "NewsDataBase").build()
								INSTANCE = instance
						}
						return instance
				}
		}

}