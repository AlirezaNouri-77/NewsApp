package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.NewsScreen
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel

@Composable
fun NewsNavigation(
		newsViewModel : NewsViewModel,
		localViewModel: LocalViewModel,
) {

		val navHostController = rememberNavController()

		NavHost(navController = navHostController, startDestination = NavigationRoute.NewsScreen.route ){
				composable(NavigationRoute.NewsScreen.route){
						NewsScreen(newsViewModel = newsViewModel)
				}
		}

}

sealed class NavigationRoute (var route:String) {
		data object NewsScreen: NavigationRoute("NewsScreen")
		data object ReadLater: NavigationRoute("ReadLater")
}