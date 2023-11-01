package com.example.newsapp.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.local.dao.NewsDao
import com.example.newsapp.local.dao.SettingDao
import com.example.newsapp.local.mapper.SettingTypeConverter
import com.example.newsapp.local.model.NewsEntity
import com.example.newsapp.local.model.SettingEntity
import com.example.newsapp.constant.SettingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@TypeConverters(value = [SettingTypeConverter::class])
@Database(entities = [NewsEntity::class, SettingEntity::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
		abstract fun NewsDao(): NewsDao
		abstract fun SettingDao(): SettingDao

		companion object {

				@Volatile
				private var INSTANCE: NewsRoomDatabase? = null

				fun getInstance(context: Context): NewsRoomDatabase {
						return INSTANCE ?: synchronized(this) {
								INSTANCE ?: newsDataBase(context)
						}
				}

				private fun newsDataBase(context: Context) =
						Room.databaseBuilder(context, NewsRoomDatabase::class.java, "NewsDataBase").addCallback(
								object : Callback() {
										override fun onCreate(db: SupportSQLiteDatabase) {
												super.onCreate(db)
												CoroutineScope(Dispatchers.IO).launch {
														getInstance(context).SettingDao()
																.insertSetting(SettingList.PrepopulateDatabase)
												}
										}
								}
						).build()

		}
}