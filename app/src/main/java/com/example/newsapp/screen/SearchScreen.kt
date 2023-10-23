package com.example.newsapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.encodeStringNavigation
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.LoadingShimmer
import com.example.newsapp.screenComponent.NewsListItem

@Composable
fun SearchScreen(
		newsSearchViewModel: NewsSearchViewModel,
		localViewModel: LocalViewModel,
		navController: NavHostController,
) {

		val searchState = newsSearchViewModel.readSearchNewsFlow().collectAsStateWithLifecycle().value

		Column(
				modifier = Modifier
						.fillMaxSize(),
		) {
				when (searchState) {
						is BaseViewModelContract.BaseState.Success -> {
								LazyColumn {
										val data = searchState.data as NewsModel
										data.results.forEach {
												item {
														NewsListItem(
																listData = it, showBookmarkIcon = true,
																clickOnItem = { articleClicked ->
																		val content = articleClicked.content.encodeStringNavigation()
																		val imageUrl = articleClicked.image_url?.encodeStringNavigation()
																		val title = articleClicked.title
																		val pubDate = articleClicked.pubDate
																		val articleId = articleClicked.article_id
																		val link = articleClicked.link.encodeStringNavigation()
																		val description = articleClicked.description
																		navController.navigate(
																				"DetailScreen/${content}/${imageUrl}/${title}/${pubDate}/${articleId}/${link}/${description}"
																		)
																},
																isBookmarked = localViewModel.articleIdList.contains(it.article_id),
														)
												}
										}
								}
						}
						is BaseViewModelContract.BaseState.Loading -> {
								LoadingShimmer()
						}
						else -> {}
				}
		}
}
