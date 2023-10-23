package com.example.newsapp.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.SettingEntity
import com.example.newsapp.local.model.SettingListDataClass
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {

		@Insert
		fun insertSetting(settingEntity: SettingEntity)

		@Query("DELETE FROM SettingTable")
		fun deleteAll()

		@Query("UPDATE SettingTable SET settingList =:settingEntity , activeSettingSection =:settingSection WHERE ID = 1")
		fun updateSettings(settingEntity: SettingListDataClass, settingSection: ActiveSettingSectionEnum)

		@Query("SELECT * FROM SettingTable")
		fun getLanguageSettingList(): Flow<List<SettingEntity>>

}