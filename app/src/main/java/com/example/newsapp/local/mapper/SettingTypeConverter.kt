package com.example.newsapp.local.mapper

import androidx.room.TypeConverter
import com.example.newsapp.local.model.SettingListDataClass
import com.google.gson.Gson

class SettingTypeConverter {
		@TypeConverter
		fun SettingListToJson(settingListDataClass: SettingListDataClass):String = Gson().toJson(settingListDataClass)
		@TypeConverter
		fun JsonToSettingList(jsonString: String):SettingListDataClass = Gson().fromJson(jsonString , SettingListDataClass::class.java)
}