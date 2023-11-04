package com.example.newsapp.Domain.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.Model.ActiveSettingSectionEnum
import com.example.newsapp.Domain.Model.SettingModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {

		@Insert
		fun insertSetting(settingEntity: SettingEntity)

		@Query("DELETE FROM SettingTable")
		fun deleteAll()

		@Query("UPDATE SettingTable SET settingList =:settingEntity , activeSettingSection =:settingSection WHERE ID = 1")
		fun updateSettings(settingEntity: List<SettingModel>, settingSection: ActiveSettingSectionEnum)

		@Query("SELECT * FROM SettingTable")
		fun getSettings(): Flow<List<SettingEntity>>

}