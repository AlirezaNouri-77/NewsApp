package com.example.newsapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.screenComponent.NewsListItem
import com.example.newsapp.util.navToDetailScreen

@Composable
fun BookmarkScreen(
		localViewModel: LocalViewModel,
		navController: NavController,
) {

		val stateLocal = localViewModel.baseState.collectAsStateWithLifecycle().value
		LaunchedEffect(key1 = Unit, block = {
				localViewModel.setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
		})

		when (stateLocal) {
				is BaseViewModelContract.BaseState.Success -> {
						val data = stateLocal.data as List<Article>
						LazyColumn(modifier = Modifier.fillMaxSize()) {
								items(items = data) {
										NewsListItem(
												data = it, clickOnItem = { articleClicked ->
														Log.d("TAG2352352", "BookmarkScreen: " + articleClicked.image_url)
														navController.navToDetailScreen(data = articleClicked)
												}
										)
								}
						}
				}

				is BaseViewModelContract.BaseState.Empty -> {
						Column(
								modifier = Modifier.fillMaxSize(),
								verticalArrangement = Arrangement.Center,
								horizontalAlignment = Alignment.CenterHorizontally,
						) {
								Text(
										text = "Nothing Saved",
										fontSize = 18.sp,
										style = MaterialTheme.typography.titleLarge,
								)
						}
				}

				else -> {}
		}

}