package com.example.newsapp.View.ScreenComponent.bottomBar

import NewsAppTheme
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.View.navigation.NavigationRoute

@Composable
fun NewsBottomBar(
  navController: NavHostController,
) {
  
  val bottomListItem = listOf(
	BottomNavigationSealed.Home,
	BottomNavigationSealed.Search,
	BottomNavigationSealed.List,
  )
  
  val current = navController.currentBackStackEntry?.destination?.route
  
  NavigationBar(
	containerColor = MaterialTheme.colorScheme.background,
	contentColor = MaterialTheme.colorScheme.secondaryContainer,
	tonalElevation = 0.dp,
  ) {
	bottomListItem.forEach {
	  NavigationBarItem(
		selected = current == it.route.route,
		onClick = {
		  if (current != it.route.route) {
			navController.navigate(it.route.route)
		  }
		},
		icon = {
		  Image(
			painter = painterResource(id = it.icon),
			contentDescription = "",
			colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
		  )
		},
		label = {
		  Text(
			text = it.title,
			fontWeight = FontWeight.Medium,
			fontSize = 14.sp,
		  )
		},
		alwaysShowLabel = false,
	  )
	}
  }
  
}

sealed class BottomNavigationSealed(
  var title: String,
  var icon: Int,
  var route: NavigationRoute
) {
  data object Home : BottomNavigationSealed(
	title = "News",
	icon = R.drawable.iconsnews,
	route = NavigationRoute.NewsScreen
  )
  
  data object Search : BottomNavigationSealed(
	title = "Search",
	icon = R.drawable.iconssearch,
	route = NavigationRoute.SearchScreen
  )
  
  data object List : BottomNavigationSealed(
	title = "Bookmark",
	icon = R.drawable.iconsbookmark,
	route = NavigationRoute.BookmarkScreen
  )
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsBottomNavigation() {
  NewsAppTheme {
	NewsBottomBar(rememberNavController())
  }
}
