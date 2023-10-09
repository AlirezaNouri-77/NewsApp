package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.application.ApplicationClass
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
						viewModels<LocalViewModel> { appDependencyContainer.roomViewModelFactory }.value
				val newsSearchViewModel =
						viewModels<NewsSearchViewModel> { appDependencyContainer.searchnewsViewModelFactory }.value

				setContent {

						MainScreen(
								newsViewModel = newsViewModel,
								localViewModel = localViewModel,
								newsSearchViewModel = newsSearchViewModel
						)

				}
		}
}


