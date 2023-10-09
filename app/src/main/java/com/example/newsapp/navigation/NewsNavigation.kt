package com.example.newsapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screen.DetailScreen
import com.example.newsapp.screen.NewsScreen
import com.example.newsapp.screen.SearchScreen

@Composable
fun NewsNavigation(
		newsViewModel: NewsViewModel,
		localViewModel: LocalViewModel,
		newsSearchViewModel: NewsSearchViewModel,
		navHostController: NavHostController,
		padding: PaddingValues,
) {

		NavHost(
				navController = navHostController,
				startDestination = NavigationRoute.HomeScreen.route,
				modifier = Modifier.padding(padding)
		) {

				composable(
						NavigationRoute.HomeScreen.route,
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
						},
				) {
						NewsScreen(
								newsViewModel = newsViewModel,
								navController = navHostController,
						)

				}

				composable(
						route = NavigationRoute.DetailScreen.route,
						arguments = listOf(
								navArgument(name = "content") {
										type = NavType.StringType
								},
								navArgument(name = "imageurl") {
										type = NavType.StringType
										nullable = true
								},
								navArgument(name = "title") {
										type = NavType.StringType
								},
								navArgument(name = "pubDate") {
										type = NavType.StringType
								},
								navArgument(name = "articleId") {
										type = NavType.StringType
								},
						)
				) { navbackstack ->

						DetailScreen(
								content = navbackstack.arguments?.getString("content")!!.replace("$$$","/"),
								title = navbackstack.arguments?.getString("title").toString(),
								imageurl = navbackstack.arguments?.getString("imageurl")!!.replace("$$$","/"),
								pubDate = navbackstack.arguments?.getString("pubDate").toString(),
								articleId = navbackstack.arguments?.getString("articleId").toString(),
						)

				}

				composable(
						NavigationRoute.SearchScreen.route,
						enterTransition = {
								slideIntoContainer(
										towards = AnimatedContentTransitionScope.SlideDirection.Left,
										animationSpec = (tween(500)),
								)
						},
						exitTransition = {
								slideOutOfContainer(
										towards = AnimatedContentTransitionScope.SlideDirection.Right,
										animationSpec = (tween(500)),
								)
						},
				) {
						SearchScreen(
								newsSearchViewModel = newsSearchViewModel,
						)
				}


		}
}

sealed class NavigationRoute(var route: String) {
		data object HomeScreen : NavigationRoute("HomeScreen")
		data object ReadLater : NavigationRoute("ReadLater")
		data object SearchScreen : NavigationRoute("SearchScreen")
		data object DetailScreen :
				NavigationRoute("DetailScreen/{content}/{imageurl}/{title}/{pubDate}/{articleId}")
}