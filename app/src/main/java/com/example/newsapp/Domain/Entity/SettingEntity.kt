package com.example.newsapp.Domain.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.Domain.Model.ActiveSettingSectionEnum
import com.example.newsapp.Domain.Model.SettingModel

@Entity(tableName = "SettingTable")
data class SettingEntity(
		@PrimaryKey(autoGenerate = true)
		var id: Int = 0,
		var activeSettingSection: ActiveSettingSectionEnum,
		var settingList: List<SettingModel>,
)



