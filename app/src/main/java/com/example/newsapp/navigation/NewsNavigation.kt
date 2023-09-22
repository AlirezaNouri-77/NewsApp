package com.example.newsapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.NewsScreen
import com.example.newsapp.SearchScreen
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel

@Composable
fun NewsNavigation(
		newsViewModel: NewsViewModel,
		localViewModel: LocalViewModel,
		navHostController: NavHostController,
		modifier: Modifier,
) {

		NavHost(
				navController = navHostController,
				startDestination = NavigationRoute.HomeScreen.route
		) {
				composable(NavigationRoute.HomeScreen.route,
						enterTransition = {
								slideIntoContainer(
										towards = AnimatedContentTransitionScope.SlideDirection.Right,
										animationSpec = (tween(500))
								)
						},
						exitTransition = {
								slideOutOfContainer(
										towards = AnimatedContentTransitionScope.SlideDirection.Left,
										animationSpec = (tween(500))
								)
						}) {
						NewsScreen(
								newsViewModel = newsViewModel,
								modifier = modifier,
						)
				}
				composable(NavigationRoute.SearchScreen.route,
						enterTransition = {
								slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,
										animationSpec = (tween(500)),)
						},
						exitTransition = {
								slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,
										animationSpec = (tween(500)),)
						},
						) {
						SearchScreen(modifier = modifier)
				}
		}

}

sealed class NavigationRoute(var route: String) {
		data object HomeScreen : NavigationRoute("HomeScreen")
		data object ReadLater : NavigationRoute("ReadLater")
		data object SearchScreen : NavigationRoute("SearchScreen")
}