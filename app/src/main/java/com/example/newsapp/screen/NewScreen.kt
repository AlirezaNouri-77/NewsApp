package com.example.newsapp.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.ChipsCategory
import com.example.newsapp.screenComponent.LoadingPagingItem
import com.example.newsapp.screenComponent.LoadingPagingItemFail
import com.example.newsapp.screenComponent.LoadingShimmerScreen
import com.example.newsapp.screenComponent.NewsListItem
import com.example.newsapp.util.isBottomList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
		newsViewModel: NewsViewModel,
		navController: NavHostController,
) {

		val stateListLazy = rememberLazyListState()
		val newsStateFlow = newsViewModel.baseState.collectAsStateWithLifecycle().value
		val newsEventFlow = newsViewModel.baseEVENT.collectAsStateWithLifecycle(
				initialValue = BaseViewModelContract.BaseEvent.Idle
		).value

		LaunchedEffect(key1 = Unit, block = {
				stateListLazy.scrollToItem(newsViewModel.newsListScrollState.intValue)
		})

		LaunchedEffect(key1 = stateListLazy) {
				snapshotFlow {
						stateListLazy.firstVisibleItemIndex
				}.debounce(700L).collectLatest { newsViewModel.newsListScrollState.intValue = it }
		}

		Log.d("TAG890", "NewsScreen: " + newsViewModel.newsMutableList.size)

		if(stateListLazy.isBottomList().value) {
				if (newsViewModel.canPaging.value) {
						newsViewModel.setBaseEffects(
								BaseViewModelContract.BaseEffect.GetData(
										userInput = newsViewModel.newsCategory.value,
										page = newsViewModel.nextPage.value,
								)
						)
				}
		}

		LazyColumn(state = stateListLazy) {

				stickyHeader {
						ChipsCategory(
								newsViewModel = newsViewModel,
								onClick = { string, int ->
										newsViewModel.newsCategoryState.intValue = int
										newsViewModel.newsCategory.value = string
										newsViewModel.clearPaging()
										newsViewModel.setBaseEffects(
												BaseViewModelContract.BaseEffect.GetData(
														userInput = string,
														page = ""
												)
										)
								},

								)
				}

				items(
						items = newsViewModel.newsMutableList,
				) {
						key(it.article_id) {
								NewsListItem(
										data = it,
										clickOnItem = { articleClicked ->

												val content = articleClicked.content.replace("/", "$$$")
												val imageUrl = articleClicked.image_url?.replace("/", "$$$")
												val title = articleClicked.title
												val pubDate = articleClicked.pubDate

												navController.navigate(
														"DetailScreen/${content}/${imageUrl}/${title}/${pubDate}"
												)

										},
								)
						}
				}

				item {
						when (newsStateFlow) {
								is BaseViewModelContract.BaseState.Loading -> {
										if (newsViewModel.newsMutableList.size == 0) {
												LoadingShimmerScreen()
										} else {
												LoadingPagingItem()
										}
								}
								else -> {}
						}
						when (newsEventFlow) {
								is BaseViewModelContract.BaseEvent.EventError -> {
										if (newsViewModel.newsMutableList.size == 0) {
												Text(
														text = "Failed to get news please check your internet connection or try later" + "\n" +
																		newsEventFlow.message
												)
										} else {
												LoadingPagingItemFail(
														onClickTryAgain = {

														},
												)
										}
								}
								else -> {}
						}
				}
		}
}