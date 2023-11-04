package com.example.newsapp.Presentation.Screen

import NewsAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.newsapp.application.ApplicationClass
import com.example.newsapp.Presentation.ViewModel.LocalViewModel
import com.example.newsapp.Presentation.ViewModel.NewsSearchViewModel
import com.example.newsapp.Presentation.ViewModel.NewsViewModel

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

