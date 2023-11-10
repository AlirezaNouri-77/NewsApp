package com.example.newsapp.Domain.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.Model.ActiveSettingSection
import com.example.newsapp.Domain.Model.SettingModel

@Dao
interface SettingDao {
  
  @Insert
  fun insertSetting(settingEntity: SettingEntity)
  
  @Query("DELETE FROM SettingTable")
  fun deleteAll()
  
  @Query("UPDATE SettingTable SET settingList =:settingEntity , activeSettingSection =:settingSection WHERE ID = 1")
  fun updateSettings(settingEntity: List<SettingModel>, settingSection: ActiveSettingSection)
  
  @Query("SELECT * FROM SettingTable")
  fun getSettings(): List<SettingEntity>
  
}