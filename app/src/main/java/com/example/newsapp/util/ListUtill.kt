package com.example.newsapp.util

import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.SettingDataClass
import com.example.newsapp.local.model.SettingEntity
import com.example.newsapp.local.model.SettingListDataClass

data object ListUtil {
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
		)

		val PrepopulateItem = SettingEntity(
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

