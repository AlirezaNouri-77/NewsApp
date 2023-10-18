package com.example.newsapp.screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.local.viewmodel.LocalViewModel
import com.example.newsapp.navigation.encodeStringNavigation
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.ChipsCategory
import com.example.newsapp.screenComponent.LoadingPagingItemFail
import com.example.newsapp.screenComponent.LoadingShimmer
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
		localViewModel: LocalViewModel,
) {

		val stateListLazy = rememberLazyListState()
		val newsStateFlow = newsViewModel.baseState.collectAsStateWithLifecycle().value

		LaunchedEffect(key1 = Unit, block = {
				stateListLazy.scrollToItem(newsViewModel.newsListScrollState.intValue)
		})

		LaunchedEffect(key1 = stateListLazy) {
				snapshotFlow {
						stateListLazy.firstVisibleItemIndex
				}.debounce(700L).collectLatest { newsViewModel.newsListScrollState.intValue = it }
		}

		if (stateListLazy.isBottomList().value && newsViewModel.canPaging.value) {
				newsViewModel.setBaseEvent(
						BaseViewModelContract.BaseEvent.GetData()
				)
		}

		LazyColumn(state = stateListLazy, modifier = Modifier.fillMaxSize()) {
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

				items(
						items = newsViewModel.newsMutableList,
						key = { it.article_id },
				) {
						NewsListItem(
								listData = it,
								clickOnItem = { articleClicked ->
										val content = articleClicked.content.encodeStringNavigation()
										val imageUrl = articleClicked.image_url?.encodeStringNavigation()
										val title = articleClicked.title
										val pubDate = articleClicked.pubDate
										val articleId = articleClicked.article_id
										val link = articleClicked.link.encodeStringNavigation()
										val description = articleClicked.description
										localViewModel.getNewsRoomData()
										navController.navigate(
												"DetailScreen/${content}/${imageUrl}/${title}/${pubDate}/${articleId}/${link}/${description}"
										)
								},
								isInReadLater = localViewModel.getAllArticleId().contains(it.article_id)
						)
				}

				item {
						when (newsStateFlow) {
								is BaseViewModelContract.BaseState.Loading -> {
										if (newsViewModel.newsMutableList.size == 0) {
												LoadingShimmer()
										}
								}
								is BaseViewModelContract.BaseState.Error -> {
										if (newsViewModel.newsMutableList.size == 0) {
												LoadingItemFail {
														newsViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.GetData()
														)
												}
										} else {
												LoadingPagingItemFail(
														onClickTryAgain = {
																newsViewModel.setBaseEvent(
																		BaseViewModelContract.BaseEvent.GetData()
																)
														},
												)
										}
								}
								else -> {}
						}
				}
		}

}


@Composable
fun LoadingItemFail(
		clickOnTryAgain: () -> Unit,
) {
		Column(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
		) {
				Image(
						painter = painterResource(id = R.drawable.warning),
						contentDescription = "",
						modifier = Modifier.aspectRatio(2f)
				)
				Text(
						text = "Failed to get news please check your internet connection or try later",
						fontSize = 16.sp,
						fontWeight = FontWeight.SemiBold,
				)
				Spacer(modifier = Modifier.height(5.dp))
				Button(
						onClick = {
								clickOnTryAgain.invoke()
						},
				) {
						Text(text = "Try Again")
				}
		}
}