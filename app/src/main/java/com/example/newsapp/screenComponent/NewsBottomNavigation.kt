package com.example.newsapp.screenComponent

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.NavigationRoute
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsBottomNavigation(
		navController: NavHostController,
) {

		val bottomListItem = listOf(
				BottomNavigationSealed.Home,
				BottomNavigationSealed.Search,
				BottomNavigationSealed.List,
		)

		val current = navController.currentBackStackEntry?.destination?.route

		NavigationBar (containerColor = Color.Transparent , contentColor = Color.Transparent , tonalElevation = 0.dp) {
				bottomListItem.forEach {
						NavigationBarItem(
								selected = current == it.route.route,
								onClick = {
										navController.navigate(it.route.route)
								},
								icon = { Icon(imageVector = it.icon, contentDescription = "") },
								label = { Text(text = it.title) }
						)
				}
		}

}

sealed class BottomNavigationSealed(
		var title: String,
		var icon: ImageVector,
		var route: NavigationRoute
) {
		data object Home : BottomNavigationSealed(
				title = "Home",
				icon = Icons.Default.Home,
				route = NavigationRoute.HomeScreen
		)

		data object Search : BottomNavigationSealed(
				title = "Search",
				icon = Icons.Default.Search,
				route = NavigationRoute.SearchScreen
		)

		data object List : BottomNavigationSealed(
				title = "Home",
				icon = Icons.Default.List,
				route = NavigationRoute.ReadLater
		)

}

@Preview(showBackground = true)
@Composable
fun PreviewNewsBottomNavigation() {
		NewsAppTheme {
				NewsBottomNavigation(rememberNavController())
		}
}
