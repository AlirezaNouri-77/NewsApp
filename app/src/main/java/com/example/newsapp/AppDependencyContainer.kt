package com.example.newsapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.local.database.RoomDbHelper
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.repository.NewsRepository
import com.example.newsapp.remote.viewmodel.NewsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson

class AppDependencyContainer (
		context:Context
) {

		private var client = HttpClient(Android) {
				engine {
						this.connectTimeout = 30_000
				}
				install(Logging) {
						this.logger = Logger.DEFAULT
				}
				install(ContentNegotiation) {
						gson()
				}
		}

		private var newsRoomDataBase = RoomDbHelper.getInstance(context)

		private var newsRepository = NewsRepository(KtorClient = client)

		var newsViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return NewsViewModel(
								newsRepository = newsRepository
						) as T
				}
		}

		var roomViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return LocalViewModel(
								db = newsRoomDataBase
						) as T
				}
		}


}