package com.example.newsapp.Domain.mapper

import androidx.room.TypeConverter
import com.example.newsapp.Domain.Model.SettingModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingTypeConverter {
  @TypeConverter
  fun SettingListToJson(settingListDataClass: List<SettingModel>): String =
	Gson().toJson(settingListDataClass, object : TypeToken<List<SettingModel>>() {}.type)
  
  @TypeConverter
  fun JsonToSettingList(jsonString: String): List<SettingModel> {
	return Gson().fromJson(jsonString, object : TypeToken<List<SettingModel>>() {}.type)
  }
}