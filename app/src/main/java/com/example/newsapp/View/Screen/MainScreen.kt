package com.example.newsapp.View.Screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.View.ScreenComponent.bottomBar.NewsBottomBar
import com.example.newsapp.View.ScreenComponent.bottomSheet.BottomSheetSetting
import com.example.newsapp.View.ScreenComponent.topBar.BookmarkTopBar
import com.example.newsapp.View.ScreenComponent.topBar.NewsTopBar
import com.example.newsapp.View.ScreenComponent.topBar.SearchTopBar
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsSearchViewModel
import com.example.newsapp.View.ViewModel.NewsViewModel
import com.example.newsapp.View.navigation.NavigationRoute
import com.example.newsapp.View.navigation.NewsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  localViewModel: LocalViewModel,
  newsViewModel: NewsViewModel,
  newsSearchViewModel: NewsSearchViewModel,
) {
  
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
		newsViewModel.apply {
		  setBaseEvent(
			BaseViewModelContract.BaseEvent.ClearPaging
		  )
		  setBaseEvent(
			BaseViewModelContract.BaseEvent.GetData()
		  )
		}
	  },
	  bottomSheetState = bottomSheetState
	)
  }
  
  Scaffold(
	modifier = Modifier.fillMaxSize(),
	containerColor = MaterialTheme.colorScheme.background,
	topBar = {
	  when (navHostController.currentDestination?.route) {
		NavigationRoute.NewsScreen.route -> {
		  NewsTopBar(
			clickOnSetting = {
			  showSettingBottomSheet.value = true
			}
		  )
		}
		
		NavigationRoute.SearchScreen.route -> {
		  SearchTopBar(
			onSearch = {
			  newsSearchViewModel.apply {
				userQuery.value = it
				setBaseEvent(BaseViewModelContract.BaseEvent.ClearPaging)
				setBaseEvent(
				  BaseViewModelContract.BaseEvent.GetData(
					userInput = it,
					page = ""
				  )
				)
			  }
			},
		  )
		}
		
		NavigationRoute.BookmarkScreen.route -> {
		  BookmarkTopBar(
			clickOnDeleteAll = {
			  localViewModel.apply {
				setBaseEvent(BaseViewModelContract.BaseEvent.DeleteAllNews)
				setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
			  }
			}
		  )
		}
	  }
	},
	bottomBar = {
	  if (currentDestination != NavigationRoute.DetailScreen.route) {
		NewsBottomBar(navController = navHostController)
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