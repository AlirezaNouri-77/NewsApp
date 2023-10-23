package com.example.newsapp.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SettingTable")
data class SettingEntity(
		@PrimaryKey(autoGenerate = true)
		var id: Int = 0,
		var activeSettingSection: ActiveSettingSectionEnum,
		var settingList: SettingListDataClass,
)

enum class ActiveSettingSectionEnum {
		Language, Country, Domain, Idle
}

data class SettingListDataClass(
		val list: List<SettingDataClass>
)

data class SettingDataClass(
		var name: String,
		var code: String,
)