package com.example.newsapp.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.remote.viewmodel.UiPagingState
import com.example.newsapp.screenComponent.ChipsCategory
import com.example.newsapp.screenComponent.LoadingItemFail
import com.example.newsapp.screenComponent.LoadingPagingItem
import com.example.newsapp.screenComponent.LoadingPagingItemFail
import com.example.newsapp.screenComponent.LoadingScreenShimmer
import com.example.newsapp.screenComponent.NewsListItem
import com.example.newsapp.util.isBottomList
import com.example.newsapp.util.navToDetailScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
		newsViewModel: NewsViewModel,
		navController: NavHostController,
		localViewModel: LocalViewModel,
) {

		val listState = rememberLazyListState()

		LaunchedEffect(key1 = Unit, block = {
				listState.scrollToItem(newsViewModel.newsListScrollState.intValue)
		})

		LaunchedEffect(key1 = listState) {
				snapshotFlow {
						listState.firstVisibleItemIndex
				}.debounce(700L).collectLatest { newsViewModel.newsListScrollState.intValue = it }
		}

		if (listState.isBottomList().value && newsViewModel.canPaging && newsViewModel.UiState.value == UiPagingState.Idle) {
				newsViewModel.setBaseEvent(
						BaseViewModelContract.BaseEvent.GetData()
				)
		}

		LazyColumn(state = listState) {
				stickyHeader {
						ChipsCategory(
								newsViewModel = newsViewModel,
								onClick = { string, int ->
										newsViewModel.newsCategoryState.intValue = int
										newsViewModel.newsCategory.value = string
										newsViewModel.clearPaging()
										newsViewModel.setBaseEvent(
												BaseViewModelContract.BaseEvent.GetData()
										)
								},
						)
				}

				itemsIndexed(
						items = newsViewModel.newsMutableList,
						key = { index, item -> item.article_id }
				) { index, item ->
						NewsListItem(
								data = item,
								clickOnItem = { articleClicked ->
										newsViewModel.newsListScrollState.intValue = index
										navController.navToDetailScreen(data = articleClicked)
								},
								isBookmarked = localViewModel.articleIdList.contains(item.article_id),
								showBookmarkIcon = true,
						)
						Divider(
								thickness = 1.dp,
								color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
								modifier = Modifier.padding(5.dp),
						)
				}

				item(
						key = newsViewModel.UiState.value
				) {
						when (newsViewModel.UiState.value) {
								UiPagingState.Loading -> {
										LoadingScreenShimmer(modifier = Modifier.fillParentMaxSize())
								}

								UiPagingState.PagingLoading -> {
										LoadingPagingItem()
								}

								//todo enum
								UiPagingState.Empty -> {
										LoadingItemFail(
												modifier = Modifier.fillParentMaxSize(),
												clickOnTryAgain = {
														newsViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData()
														)
												},
												massageText = "Nothing found please change your setting or category",
												iconInt = R.drawable.nothing,
										)
								}

								UiPagingState.Error -> {
										LoadingItemFail(
												modifier = Modifier.fillParentMaxSize(),
												clickOnTryAgain = {
														newsViewModel.setBaseEvent(
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
														newsViewModel.setBaseEvent(
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