package com.example.newsapp.Data.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.Domain.Dao.NewsDao
import com.example.newsapp.Domain.Dao.SettingDao
import com.example.newsapp.Domain.Entity.NewsEntity
import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.mapper.SettingTypeConverter
import com.example.newsapp.constant.ConstantData
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
				.insertSetting(ConstantData.PrepopulateDatabase)
			}
		  }
		}
	  ).build()
	
  }
}