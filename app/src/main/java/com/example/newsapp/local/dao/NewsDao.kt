package com.example.newsapp.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsapp.local.model.NewsEntity

@Dao
interface NewsDao {
		@Transaction
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