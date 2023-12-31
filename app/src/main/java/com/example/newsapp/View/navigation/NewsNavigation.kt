package com.example.newsapp.View.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.View.Screen.BookmarkScreen
import com.example.newsapp.View.Screen.DetailScreen
import com.example.newsapp.View.Screen.NewsScreen
import com.example.newsapp.View.Screen.SearchScreen
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsSearchViewModel
import com.example.newsapp.View.ViewModel.NewsViewModel
import java.util.Base64

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
	startDestination = NavigationRoute.NewsScreen.route,
	modifier = Modifier.padding(padding),
  ) {
	
	composable(
	  NavigationRoute.NewsScreen.route,
	) {
	  NewsScreen(
		newsViewModel = newsViewModel,
		navController = navHostController,
		localViewModel = localViewModel,
	  )
	}
	
	composable(
	  route = NavigationRoute.DetailScreen.route,
	  arguments = listOf(
		navArgument(name = "content") {
		  type = NavType.StringType
		  nullable = true
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
		navArgument(name = "link") {
		  type = NavType.StringType
		},
		navArgument(name = "description") {
		  type = NavType.StringType
		  nullable = true
		},
		navArgument(name = "source") {
		  type = NavType.StringType
		},
	  ),
	) { navbackstack ->
	  DetailScreen(
		localViewModel = localViewModel,
		content = navbackstack.arguments?.getString("content")
		  ?.decodeStringNavigation()
		  ?: "",
		title = navbackstack.arguments?.getString("title").toString(),
		imageurl = navbackstack.arguments?.getString("imageurl")
		  ?.decodeStringNavigation()
		  ?: "",
		pubDate = navbackstack.arguments?.getString("pubDate").toString(),
		articleId = navbackstack.arguments?.getString("articleId").toString(),
		link = navbackstack.arguments?.getString("link")?.decodeStringNavigation()
		  ?: "",
		description = navbackstack.arguments?.getString("description").toString(),
		source = navbackstack.arguments?.getString("source").toString(),
		backClick = {
		  navHostController.popBackStack()
		}
	  )
	}
	
	composable(
	  NavigationRoute.BookmarkScreen.route,
	) {
	  BookmarkScreen(
		localViewModel = localViewModel,
		navController = navHostController,
	  )
	}
	
	composable(
	  NavigationRoute.SearchScreen.route,
	) {
	  SearchScreen(
		newsSearchViewModel = newsSearchViewModel,
		localViewModel = localViewModel,
		navController = navHostController,
	  )
	}
	
  }
}

fun String.encodeStringNavigation(): String {
  return Base64.getUrlEncoder().encodeToString(this.toByteArray())
}

fun String.decodeStringNavigation(): String {
  return String(Base64.getUrlDecoder().decode(this))
}

sealed class NavigationRoute(var route: String) {
  data object NewsScreen : NavigationRoute("NewsScreen")
  data object BookmarkScreen : NavigationRoute("BookmarkScreen")
  data object SearchScreen : NavigationRoute("SearchScreen")
  data object DetailScreen :
	NavigationRoute("DetailScreen/{content}/{imageurl}/{title}/{pubDate}/{articleId}/{link}/{description}/{source}")
}