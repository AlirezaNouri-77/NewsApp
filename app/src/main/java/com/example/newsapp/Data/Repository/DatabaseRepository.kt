package com.example.newsapp.Data.Repository

import com.example.newsapp.Data.Db.NewsRoomDatabase
import com.example.newsapp.Domain.Entity.NewsEntity
import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.Model.ActiveSettingSection
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.SettingModel
import com.example.newsapp.Domain.Repository.LocalRepository
import com.example.newsapp.Domain.mapper.ModelMapper

class DatabaseRepository(
  private var database: NewsRoomDatabase,
  private var modelMapper: ModelMapper,
) : LocalRepository {
  
  override fun getAllNews(): List<NewsEntity> {
	return database.NewsDao().getAllNews()
  }
  
  override fun insertNews(article: Article) {
	database.NewsDao().insertNews(modelMapper.articleToRoomEntity(article))
  }
  
  override fun deleteNews(articleID: String) {
	database.NewsDao().deleteNews(articleID)
  }
  
  override fun getAllArticleId(): List<String> {
	return database.NewsDao().getArticleIdList()
  }
  
  override fun deleteAllNews() {
	database.NewsDao().deleteAll()
  }
  
  override suspend fun getSettings(): List<SettingEntity> {
	return database.SettingDao().getSettings()
  }
  
  override fun updateSetting(
	settingEntity: List<SettingModel>,
	settingSection: ActiveSettingSection
  ) {
	database.SettingDao()
	  .updateSettings(settingEntity = settingEntity, settingSection = settingSection)
  }
  
}