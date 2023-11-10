package com.example.newsapp.View.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.UiPagingState
import com.example.newsapp.R
import com.example.newsapp.View.ScreenComponent.ChipsCategory
import com.example.newsapp.View.ScreenComponent.LoadingItemFail
import com.example.newsapp.View.ScreenComponent.LoadingPagingItem
import com.example.newsapp.View.ScreenComponent.LoadingPagingItemFail
import com.example.newsapp.View.ScreenComponent.LoadingScreenShimmer
import com.example.newsapp.View.ScreenComponent.NewsListItem
import com.example.newsapp.View.ViewModel.LocalViewModel
import com.example.newsapp.View.ViewModel.NewsViewModel
import com.example.newsapp.View.util.isBottomList
import com.example.newsapp.View.util.navToDetailScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
  newsViewModel: NewsViewModel,
  localViewModel: LocalViewModel,
  navController: NavHostController,
) {
  
  val listState = rememberLazyListState()
  
  LaunchedEffect(key1 = Unit, block = {
	listState.scrollToItem(newsViewModel.newsListScrollState.intValue)
  })
  
  var newsCategoryState by remember { mutableIntStateOf(newsViewModel.newsCategoryState) }
  
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
		itemSelect = newsCategoryState,
		onClick = { string, int ->
		  newsViewModel.apply {
			newsCategoryState = int
			newsCategory.value = string
			setBaseEvent(BaseViewModelContract.BaseEvent.ClearPaging)
			setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
		  }
		},
	  )
	}
	
	itemsIndexed(
	  items = newsViewModel.newsMutableList,
	  key = { index, item ->
		item.article_id
	  },
	  contentType = { index, item -> item.content },
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
	  key = newsViewModel.UiState.value,
	) {
	  when (newsViewModel.UiState.value) {
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
			  newsViewModel.setBaseEvent(
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
			  newsViewModel.setBaseEvent(
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
