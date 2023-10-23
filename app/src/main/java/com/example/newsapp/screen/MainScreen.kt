package com.example.newsapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.NavigationRoute
import com.example.newsapp.navigation.NewsNavigation
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.topBar.BookmarkTopBar
import com.example.newsapp.screenComponent.BottomSheetSetting
import com.example.newsapp.screenComponent.NewsBottomNavigation
import com.example.newsapp.screenComponent.topBar.NewsTopBar
import com.example.newsapp.screenComponent.topBar.SearchTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
		newsViewModel: NewsViewModel,
		localViewModel: LocalViewModel,
		newsSearchViewModel: NewsSearchViewModel,) {

		val navHostController = rememberNavController()
		val showSettingBottomSheet = remember {
				mutableStateOf(false)
		}
		val bottomSheetState = rememberModalBottomSheetState()

		navHostController.currentBackStackEntryFlow.collectAsState(initial = NavigationRoute.NewsScreen).value
		val currentDestination = navHostController.currentDestination?.route

		if (showSettingBottomSheet.value) {
				BottomSheetSetting(
						newsViewModel = newsViewModel,
						onDismiss = {

								showSettingBottomSheet.value = false
						},
						bottomSheetState = bottomSheetState
				)
		}

		Scaffold(
				modifier = Modifier.fillMaxSize(),
				topBar = {
						when (navHostController.currentDestination?.route) {
								NavigationRoute.NewsScreen.route -> {
										NewsTopBar(
												newsViewModel = newsViewModel,
												clickOnSetting = {
														showSettingBottomSheet.value = true
												}
										)
								}

								NavigationRoute.SearchScreen.route -> {
										SearchTopBar(
												onSearch = {
														newsSearchViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData(
																		userInput = it,
																		page = ""
																)
														)
												},
										)
								}

								NavigationRoute.BookmarkScreen.route -> {
										BookmarkTopBar(
												clickOnDeleteAll = {
														localViewModel.deleteAllItem()
														localViewModel.setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
												}
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