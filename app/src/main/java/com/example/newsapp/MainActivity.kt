package com.example.newsapp

import NewsAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.newsapp.View.Screen.MainScreen
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsSearchViewModel
import com.example.newsapp.View.ViewModel.NewsViewModel
import com.example.newsapp.application.ApplicationClass

class MainActivity : ComponentActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	
	val appDependencyContainer = (application as ApplicationClass).diContainer
	
	val newsViewModel =
	  viewModels<NewsViewModel> { appDependencyContainer.newsViewModelFactory }.value
	val localViewModel =
	  viewModels<LocalViewModel> { appDependencyContainer.localViewModelFactory }.value
	val newsSearchViewModel =
	  viewModels<NewsSearchViewModel> { appDependencyContainer.searchNewsViewModelFactory }.value
	
	setContent {
	  NewsAppTheme {
		MainScreen(
		  localViewModel = localViewModel,
		  newsViewModel = newsViewModel,
		  newsSearchViewModel = newsSearchViewModel
		)
	  }
	}
  }
  
}

