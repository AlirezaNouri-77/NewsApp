package com.example.newsapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.screenComponent.LoadingShimmerScreen
import com.example.newsapp.screenComponent.NewsListItem

@Composable
fun SearchScreen(
		newsSearchViewModel: NewsSearchViewModel
) {

		val searchstate = newsSearchViewModel.readSearchNewsFlow().collectAsStateWithLifecycle().value

		Column(
				modifier = Modifier
						.fillMaxSize(),
		) {

				when (searchstate) {

						is BaseViewModelContract.BaseState.Success -> {

								LazyColumn {

										val data = searchstate.data as NewsModel

										data.results.forEach {
												item {
														NewsListItem(data = it)
												}
										}

								}
						}

						is BaseViewModelContract.BaseState.Loading -> {

								LoadingShimmerScreen()

						}

						else -> {}
				}


		}

}
