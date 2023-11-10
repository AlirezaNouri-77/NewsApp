package com.example.newsapp.Domain.Repository

import com.example.newsapp.Domain.Entity.NewsEntity
import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.Model.ActiveSettingSection
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.SettingModel

interface LocalRepository {
  fun getAllNews(): List<NewsEntity>
  fun insertNews(article: Article)
  fun deleteNews(articleID: String)
  fun getAllArticleId(): List<String>
  fun deleteAllNews()
  
  suspend fun getSettings(): List<SettingEntity>
  fun updateSetting(settingEntity: List<SettingModel>, settingSection: ActiveSettingSection)
  
}