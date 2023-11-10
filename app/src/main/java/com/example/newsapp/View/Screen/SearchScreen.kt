package com.example.newsapp.View.Screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.UiPagingState
import com.example.newsapp.R
import com.example.newsapp.View.ScreenComponent.LoadingItemFail
import com.example.newsapp.View.ScreenComponent.LoadingPagingItem
import com.example.newsapp.View.ScreenComponent.LoadingPagingItemFail
import com.example.newsapp.View.ScreenComponent.LoadingScreenShimmer
import com.example.newsapp.View.ScreenComponent.NewsListItem
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsSearchViewModel
import com.example.newsapp.View.util.isBottomList
import com.example.newsapp.View.util.navToDetailScreen

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
			messageText = "Nothing found please change your setting or category",
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
			messageText = "Failed to get news please check your internet connection or try later",
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