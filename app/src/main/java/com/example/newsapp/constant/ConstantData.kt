package com.example.newsapp.constant

import com.example.newsapp.Domain.Entity.SettingEntity
import com.example.newsapp.Domain.Model.ActiveSettingSection
import com.example.newsapp.Domain.Model.SettingModel

data object ConstantData {
  
  val languageList: Array<SettingModel> = arrayOf(
	SettingModel(name = "English", code = "en"),
	SettingModel(name = "Chinese", code = "zh"),
	SettingModel(name = "French", code = "fr"),
	SettingModel(name = "Hebrew", code = "he"),
	SettingModel(name = "German", code = "de"),
	SettingModel(name = "Italian", code = "it"),
	SettingModel(name = "Korean", code = "ko"),
	SettingModel(name = "Japanese", code = "jp"),
	SettingModel(name = "Persian", code = "fa"),
	SettingModel(name = "Russian", code = "ru"),
	SettingModel(name = "Turkish", code = "tr"),
	SettingModel(name = "Spanish", code = "es"),
  )
  
  val countryList: Array<SettingModel> = arrayOf(
	SettingModel(name = "USA", code = "us"),
	SettingModel(name = "United kingdom", code = "gb"),
	SettingModel(name = "United arab emirates", code = "ae"),
	SettingModel(name = "Turkey", code = "tr"),
	SettingModel(name = "North korea", code = "kp"),
	SettingModel(name = "Japan", code = "jp"),
	SettingModel(name = "Israel", code = "il"),
  )
  
  val domainList: Array<SettingModel> = arrayOf(
	SettingModel(name = "BBC", code = "bbc"),
	SettingModel(name = "GNZ", code = "gnz"),
	SettingModel(name = "New York Times", code = "nytimes"),
	SettingModel(name = "Hollywood Life", code = "hollywoodlife"),
	SettingModel(name = "TechRadar", code = "techradar"),
	SettingModel(name = "Digital Trends", code = "digitaltrends"),
	SettingModel(name = "North Wales Pioneer", code = "northwalespioneer"),
	SettingModel(name = "South Wales Guardian", code = "southwalesguardian"),
	SettingModel(name = "Daily Mail Uk", code = "dailymailuk"),
	
	)
  
  val chipsItems = arrayOf(
	"Top",
	"Business",
	"Politics",
	"Entertainment",
	"Health",
	"Science",
	"Sports",
	"Technology",
	"Environment",
	"Food",
	"World",
	"Tourism",
  )
  
  val PrepopulateDatabase = SettingEntity(
	id = 0,
	activeSettingSection = ActiveSettingSection.LANGUAGE,
	settingList = listOf(
	  SettingModel(
		name = "English",
		code = "en"
	  )
	),
  )
  
}

