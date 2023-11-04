package com.example.newsapp.Presentation.Screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.Presentation.ViewModel.LocalViewModel
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Presentation.ViewModel.NewsSearchViewModel
import com.example.newsapp.Presentation.ViewModel.UiPagingState
import com.example.newsapp.Presentation.ScreenComponent.LoadingItemFail
import com.example.newsapp.Presentation.ScreenComponent.LoadingPagingItem
import com.example.newsapp.Presentation.ScreenComponent.LoadingPagingItemFail
import com.example.newsapp.Presentation.ScreenComponent.LoadingScreenShimmer
import com.example.newsapp.Presentation.ScreenComponent.NewsListItem
import com.example.newsapp.Presentation.util.isBottomList
import com.example.newsapp.Presentation.util.navToDetailScreen

@Composable
fun SearchScreen(
		newsSearchViewModel: NewsSearchViewModel,
		localViewModel: LocalViewModel,
		navController: NavHostController,
) {

		val listState = rememberLazyListState()

		if (listState.isBottomList().value && newsSearchViewModel.canPaging.value) {
				newsSearchViewModel.setBaseEvent(
						BaseViewModelContract.BaseEvent.GetData()
				)
		}

		LazyColumn(state = listState) {
				items(items = newsSearchViewModel.searchNewsList, key = { it.article_id }) {

						NewsListItem(
								data = it,
								clickOnItem = {
										navController.navToDetailScreen(data = it)
								},
								isBookmarked = localViewModel.articleIdList.contains(it.article_id),
								showBookmarkIcon = true,
						)

				}

				item(
						key = newsSearchViewModel.uiState.value,
				) {
						when (newsSearchViewModel.uiState.value) {
								UiPagingState.Loading -> {
										LoadingScreenShimmer(modifier = Modifier.fillParentMaxSize())
								}

								UiPagingState.PagingLoading -> {
										LoadingPagingItem()
								}

								UiPagingState.Empty -> {
										LoadingItemFail(
												modifier = Modifier.fillParentMaxSize(),
												clickOnTryAgain = {
														newsSearchViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData()
														)
												},
												showRetry = false,
												massageText = "Nothing found please change your setting or category",
												iconInt = R.drawable.nothing,
										)
								}

								UiPagingState.Error -> {
										LoadingItemFail(
												modifier = Modifier.fillParentMaxSize(),
												clickOnTryAgain = {
														newsSearchViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData()
														)
												},
												massageText = "Failed to get news please check your internet connection or try later",
												iconInt = R.drawable.nowifi,
										)
								}

								UiPagingState.PagingError -> {
										LoadingPagingItemFail(
												onClickTryAgain = {
														newsSearchViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData()
														)
												},
										)
								}

								else -> {}
						}
				}

		}

}