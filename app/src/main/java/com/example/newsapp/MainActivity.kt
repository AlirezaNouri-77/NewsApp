package com.example.newsapp

import NewsAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.newsapp.application.ApplicationClass
import com.example.newsapp.local.api.LocalViewModelImp
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screen.MainScreen

class MainActivity : ComponentActivity() {

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)

				val appDependencyContainer = (application as ApplicationClass).appDependencyContainer

				val newsViewModel =
						viewModels<NewsViewModel> { appDependencyContainer.newsViewModelFactory }.value
				val localViewModel =
						viewModels<LocalViewModel> { appDependencyContainer.localViewModelFactory }.value
				val newsSearchViewModel =
						viewModels<NewsSearchViewModel> { appDependencyContainer.searchNewsViewModelFactory }.value

				setContent {
						NewsAppTheme {
								MainScreen(
										newsViewModel = newsViewModel,
										localViewModel = localViewModel,
										newsSearchViewModel = newsSearchViewModel
								)
						}
				}
		}
}

