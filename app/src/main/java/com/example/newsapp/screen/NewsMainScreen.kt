package com.example.newsapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.NavigationRoute
import com.example.newsapp.navigation.NewsNavigation
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.NewsBottomNavigation
import com.example.newsapp.screenComponent.NewsScreenTopBar
import com.example.newsapp.screenComponent.NewsSearchScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
		newsViewModel: NewsViewModel,
		localViewModel: LocalViewModel,
		newsSearchViewModel: NewsSearchViewModel
) {

		val navHostController = rememberNavController()

		navHostController.currentBackStackEntryFlow.collectAsState(initial = NavigationRoute.HomeScreen).value
		val currentDestination = navHostController.currentDestination?.route

		Scaffold(
				modifier = Modifier.fillMaxSize(), containerColor = colorScheme.background,
				topBar = {
						when (navHostController.currentDestination?.route) {

								NavigationRoute.HomeScreen.route -> {
										NewsScreenTopBar()
								}

								NavigationRoute.DetailScreen.route -> {
//										DetailScreenTopBar {
//												navHostController.popBackStack()
//										}
								}

								NavigationRoute.SearchScreen.route -> {
										NewsSearchScreenTopBar(
												onSearch = {
														if (it.length > 3) {
																newsSearchViewModel.setBaseEffects(
																		BaseViewModelContract.BaseEffect.GetData(
																				userInput = it,
																				page = ""
																		)
																)
														}
												},
										)
								}

						}

				},
				bottomBar = {
						if (currentDestination != NavigationRoute.DetailScreen.route) {
								NewsBottomNavigation(navController = navHostController)
						}
				}
		) { padding ->

				NewsNavigation(
						newsViewModel = newsViewModel,
						localViewModel = localViewModel,
						newsSearchViewModel = newsSearchViewModel,
						navHostController = navHostController,
						padding = padding,
				)

		}

}