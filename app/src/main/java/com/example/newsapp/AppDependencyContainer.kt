package com.example.newsapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.local.database.NewsRoomDatabase
import com.example.newsapp.local.mapper.EntityMapper
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.repository.NewsRepository
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.util.CheckInternet
import com.example.newsapp.util.InternetConnectionInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson
import java.util.concurrent.TimeUnit

class AppDependencyContainer(
		context: Context
) {

		private var newsRoomDataBase = NewsRoomDatabase.getInstance(context)

		private var newsRepository =
				NewsRepository(
						ktorClient = Ktor.getInstance(context = context)!!,
				)

		var searchNewsViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return NewsSearchViewModel(
								newsRepository = newsRepository
						) as T
				}
		}


		var localViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return LocalViewModel(
								database = newsRoomDataBase,
						) as T
				}
		}

		var newsViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return NewsViewModel(
								newsRepository = newsRepository,
								database = newsRoomDataBase,
						) as T
				}
		}

		object Ktor {

				@Volatile
				private var INSTANCE: HttpClient? = null

				fun getInstance(context: Context): HttpClient? {
						synchronized(this) {
								var instance = INSTANCE
								if (instance == null) {
										instance = HttpClient(io.ktor.client.engine.okhttp.OkHttp) {
												engine {
														config {
																this.addInterceptor(InternetConnectionInterceptor(context = context))
																this.callTimeout(30, TimeUnit.SECONDS)
																this.connectTimeout(30, TimeUnit.SECONDS)
														}
												}
												install(Logging) {
														this.logger = Logger.DEFAULT
														this.level = LogLevel.HEADERS
												}
												install(ContentNegotiation) {
														gson()
												}
										}
										INSTANCE = instance
								}
						}
						return INSTANCE
				}

		}

}




