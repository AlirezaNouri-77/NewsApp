package com.example.newsapp.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.local.model.RoomEntity

@Dao
interface RoomDao {

		@Query("SELECT * FROM News_Table")
		fun getAllNews(): List<RoomEntity>

		@Insert
		fun insertNews(roomEntity: RoomEntity)

		@Query("DELETE FROM News_Table")
		fun deleteAll()

		@Query("DELETE FROM News_Table WHERE articleId = :articleID ")
		fun deleteNews(articleID: String)

		@Query("SELECT EXISTS (SELECT articleId FROM News_Table WHERE articleId = :articleID )")
		fun isArticleSaved(articleID: String): Boolean

		@Query("SELECT articleId FROM News_Table")
		fun getArticleIdList():List<String>

}