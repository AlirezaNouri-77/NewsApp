package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.application.ApplicationClass
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.NavigationRoute
import com.example.newsapp.navigation.NewsNavigation
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.MyTopBar
import com.example.newsapp.screenComponent.NewsBottomNavigation

class MainActivity : ComponentActivity() {
		@OptIn(ExperimentalMaterial3Api::class)
		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)

				setContent {

						val appDependencyContainer = (application as ApplicationClass).appDependencyContainer
						val newsViewModel =
								viewModels<NewsViewModel> { appDependencyContainer.newsViewModelFactory }.value
						val localViewModel =
								viewModels<LocalViewModel> { appDependencyContainer.roomViewModelFactory }.value

						val navHostController = rememberNavController()

						LaunchedEffect(key1 = Unit, block = {
								newsViewModel.setBaseEffects(BaseViewModelContract.BaseEffect.GetData(""))
						})

						Scaffold(
								modifier = Modifier.fillMaxSize(),
								topBar = {
										MyTopBar()
								},
								bottomBar = { NewsBottomNavigation(navController = navHostController) }
						) { padding ->

								NewsNavigation(
										newsViewModel = newsViewModel,
										localViewModel = localViewModel,
										navHostController = navHostController,
										modifier = Modifier.padding(padding),
								)

						}

				}
		}
}


