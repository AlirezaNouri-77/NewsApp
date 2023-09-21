package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.newsapp.application.ApplicationClass
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.NewsNavigation
import com.example.newsapp.remote.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)

				setContent {

						val appDependencyContainer = (application as ApplicationClass).appDependencyContainer

						val newsViewModel =
								viewModels<NewsViewModel> { appDependencyContainer.newsViewModelFactory }.value

						val localViewModel =
								viewModels<LocalViewModel> { appDependencyContainer.roomViewModelFactory }.value

						Log.d("TAG22", "onCreate: " + localViewModel.baseState.collectAsState().value)

						NewsNavigation(newsViewModel = newsViewModel, localViewModel = localViewModel)

				}
		}
}


