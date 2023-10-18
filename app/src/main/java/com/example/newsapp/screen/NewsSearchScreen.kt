package com.example.newsapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.screenComponent.LoadingShimmer
import com.example.newsapp.screenComponent.NewsListItem

@Composable
fun SearchScreen(
		newsSearchViewModel: NewsSearchViewModel,
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
														NewsListItem(listData = it)
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
