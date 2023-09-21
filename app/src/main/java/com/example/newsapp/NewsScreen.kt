package com.example.newsapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.screenComponent.ChipsCategory
import com.example.newsapp.screenComponent.MyTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
		newsViewModel: NewsViewModel
) {

		val state = newsViewModel.baseState.collectAsStateWithLifecycle().value
		val density = LocalDensity.current
		val statelist = rememberLazyListState()

		val isFirstItemVisible by remember {
				derivedStateOf {
						mutableStateOf(statelist.canScrollBackward)
				}
		}

		Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
				MyTopBar()
		}) { padding ->

				Box(
						modifier = Modifier
								.padding(padding)
				) {

						when (state) {
								is BaseViewModelContract.BaseState.Success -> {

										NewsContent(
												data = state.data as NewsModel,
												density = density,
												stateList = statelist,
												isShow = isFirstItemVisible.value
										)

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

}

@Composable
fun NewsContent(
		data: NewsModel,
		density: Density,
		stateList: LazyListState,
		isShow:Boolean
		) {

		val chipItemClicked by rememberSaveable {
				mutableIntStateOf(0)
		}

		LazyColumn(
				Modifier
						.fillMaxSize(),
				state = stateList
		) {

				item {
						AnimatedVisibility(
								visible = !isShow,
								enter = slideInVertically {
										with(density) { -40.dp.roundToPx() }
								} + expandVertically(
										expandFrom = Alignment.Top
								) + fadeIn(
										initialAlpha = 0.4f
								),
								exit = slideOutVertically() + shrinkVertically() + fadeOut()
						) {
								ChipsCategory(itemIndex = chipItemClicked, onClick = { })
						}
				}

				data.articles.forEach {
						item {
								Text(
										text = it.title,
										modifier = Modifier
												.fillMaxWidth()
												.padding(
														horizontal = 10.dp,
														vertical = 10.dp
												)
								)
						}
				}
		}

}