package com.example.newsapp.constant

import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.SettingDataClass
import com.example.newsapp.local.model.SettingEntity
import com.example.newsapp.local.model.SettingListDataClass

data object SettingList {
		val languageList: List<SettingDataClass> = listOf(
				SettingDataClass(name = "English", code = "en"),
				SettingDataClass(name = "Chinese", code = "zh"),
				SettingDataClass(name = "French", code = "fr"),
				SettingDataClass(name = "Hebrew", code = "he"),
				SettingDataClass(name = "German", code = "de"),
				SettingDataClass(name = "Italian", code = "it"),
				SettingDataClass(name = "Korean", code = "ko"),
				SettingDataClass(name = "Japanese", code = "jp"),
				SettingDataClass(name = "Persian", code = "fa"),
				SettingDataClass(name = "Russian", code = "ru"),
				SettingDataClass(name = "Turkish", code = "tr"),
				SettingDataClass(name = "Spanish", code = "es"),
		)

		val countryList: List<SettingDataClass> = listOf(
				SettingDataClass(name = "USA", code = "us"),
				SettingDataClass(name = "United kingdom", code = "gb"),
				SettingDataClass(name = "United arab emirates", code = "ae"),
				SettingDataClass(name = "Turkey", code = "tr"),
				SettingDataClass(name = "North korea", code = "kp"),
				SettingDataClass(name = "Japan", code = "jp"),
				SettingDataClass(name = "Israel", code = "il"),
		)

		val domainList: List<SettingDataClass> = listOf(
				SettingDataClass(name = "BBC", code = "bbc"),
				SettingDataClass(name = "GNZ" , code = "gnz"),
				SettingDataClass(name = "New York Times" , code = "nytimes"),
				SettingDataClass(name = "Hollywood Life", code = "hollywoodlife"),
				SettingDataClass(name = "TechRadar", code = "techradar"),
				SettingDataClass(name = "Digital Trends", code = "digitaltrends"),
				SettingDataClass(name = "North Wales Pioneer", code = "northwalespioneer"),
				SettingDataClass(name = "South Wales Guardian", code = "southwalesguardian"),
				SettingDataClass(name = "Daily Mail Uk", code = "dailymailuk"),

		)

		val PrepopulateDatabase = SettingEntity(
				id = 0,
				activeSettingSection = ActiveSettingSectionEnum.Language,
				settingList = SettingListDataClass(
						listOf(
								SettingDataClass(
										name = "English",
										code = "en"
								)
						)
				),
		)

}

