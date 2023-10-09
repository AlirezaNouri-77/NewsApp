package com.example.newsapp

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.http.NetworkException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.local.database.RoomDbHelper
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.repository.NewsRepository
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.util.InternetConnectionInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
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

//		private var ktorInstance = HttpClient(Android) {
//				engine {
//						this.connectTimeout = 30_000
//				}
//				install(Logging) {
//						this.logger = Logger.DEFAULT
//				}
//				install(ContentNegotiation) {
//						gson()
//				}
//		}


		private var newsRoomDataBase = RoomDbHelper.getInstance(context)

		private var newsRepository =
				NewsRepository(ktorClient = KtorInstance.getInstance(context = context)!!)

		var newsViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return NewsViewModel(
								newsRepository = newsRepository
						) as T
				}
		}

		var searchnewsViewModelFactory = object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
						return NewsSearchViewModel(
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

		companion object {

		}

}

object KtorInstance {

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
										install(HttpRequestRetry){
												// Todo HttpRequestRetry
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


