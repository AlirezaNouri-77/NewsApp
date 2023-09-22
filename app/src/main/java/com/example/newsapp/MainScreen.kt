package com.example.newsapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.ChipsCategory
import com.example.newsapp.screenComponent.NewsListItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun NewsScreen(
		newsViewModel: NewsViewModel,
		modifier: Modifier,
) {

		val stateList = rememberLazyListState()
		val state = newsViewModel.baseState.collectAsStateWithLifecycle().value
		val density = LocalDensity.current

		LaunchedEffect(key1 = Unit, block = {
				stateList.scrollToItem(newsViewModel.newsListScrollState.intValue)
		})

		LaunchedEffect(key1 = stateList) {
				snapshotFlow {
						stateList.firstVisibleItemIndex
				}.debounce(700L).collectLatest { newsViewModel.newsListScrollState.intValue = it }
		}

		Column(
				modifier = modifier,
		) {

				AnimatedVisibility(
						visible = true,
						enter = slideInVertically {
								with(density) { -40.dp.roundToPx() }
						} + expandVertically(
								expandFrom = Alignment.Top
						) + fadeIn(
								initialAlpha = 0.4f
						),
						exit = slideOutVertically() + shrinkVertically() + fadeOut()
				) {

						// TODO Fix category save state
						ChipsCategory(
								newsViewModel = newsViewModel,
								onClick = {
										newsViewModel.setBaseEffects(
												BaseViewModelContract.BaseEffect.GetData(
														it
												)
										)
								},
								)

				}
				when (state) {
						is BaseViewModelContract.BaseState.Success -> {
								val data = state.data as NewsModel
								LazyColumn(
										Modifier
												.fillMaxSize(),
										state = stateList
								) {
										data.articles.forEach {
												if (!it.content.equals("[Removed]")) {
														item {
																NewsListItem(data = it)
														}
												}
										}
								}
						}

						is BaseViewModelContract.BaseState.Loading -> {
								Text(
										text = "Loading",
										Modifier.fillMaxSize(),
										fontSize = 18.sp,
										textAlign = TextAlign.Center
								)

						}

						else -> {}
				}
		}
}