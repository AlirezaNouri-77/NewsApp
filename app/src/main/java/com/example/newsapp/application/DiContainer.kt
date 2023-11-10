package com.example.newsapp.application

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Data.Db.NewsRoomDatabase
import com.example.newsapp.Data.Repository.DatabaseRepository
import com.example.newsapp.Data.Repository.NewsRepository
import com.example.newsapp.Domain.mapper.ModelMapper
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsSearchViewModel
import com.example.newsapp.View.ViewModel.NewsViewModel
import com.example.newsapp.View.util.InternetConnectionInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson
import java.util.concurrent.TimeUnit

class DiContainer(
  context: Context
) {
  
  private var newsRoomDataBase = NewsRoomDatabase.getInstance(context)
  
  private var modelMapper = ModelMapper()
  
  private var databaseRepository =
	DatabaseRepository(database = newsRoomDataBase, modelMapper = modelMapper)
  
  private var newsRepository =
	NewsRepository(
	  ktorClient = Ktor.getInstance(context = context)!!,
	)
  
  var searchNewsViewModelFactory = object : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
	  return NewsSearchViewModel(
		newsRepository = newsRepository,
	  ) as T
	}
  }
  
  
  var localViewModelFactory = object : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
	  return LocalViewModel(
		databaseRepository = databaseRepository,
		modelMapper = modelMapper,
	  ) as T
	}
  }
  
  var newsViewModelFactory = object : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
	  return NewsViewModel(
		newsRepository = newsRepository,
		databaseRepository = databaseRepository
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




