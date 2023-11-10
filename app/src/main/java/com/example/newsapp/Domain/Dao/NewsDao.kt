package com.example.newsapp.Domain.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.Domain.Entity.NewsEntity

@Dao
interface NewsDao {
  
  @Query("SELECT * FROM News_Table")
  fun getAllNews(): List<NewsEntity>
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertNews(newsEntity: NewsEntity)
  
  @Query("DELETE FROM News_Table")
  fun deleteAll()
  
  @Query("DELETE FROM News_Table WHERE articleId = :articleID ")
  fun deleteNews(articleID: String)
  
  @Query("SELECT articleId FROM News_Table")
  fun getArticleIdList(): List<String>
  
}