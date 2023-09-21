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

		@Delete
		fun deleteNews(roomEntity: RoomEntity)

}