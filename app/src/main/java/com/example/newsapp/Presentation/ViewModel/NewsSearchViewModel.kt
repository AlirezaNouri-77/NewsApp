package com.example.newsapp.Presentation.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.NewsModel
import com.example.newsapp.Data.Repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsSearchViewModel(
		private var newsRepository: NewsRepository,
) : ViewModel(), BaseViewModelContract {

		private var nextPage = mutableStateOf("")
		var searchNewsList = mutableListOf<Article>()
		var canPaging = mutableStateOf(false)

		var userQuery = mutableStateOf("")
		var uiState = mutableStateOf(UiPagingState.Idle)

		private var _baseState =
				MutableStateFlow<BaseViewModelContract.BaseState>(BaseViewModelContract.BaseState.Idle)
		override var baseState: StateFlow<BaseViewModelContract.BaseState>
				get() = _baseState.asStateFlow()
				set(value) {}
		private var _baseEvent = Channel<BaseViewModelContract.BaseEvent>(Channel.UNLIMITED)
		override var baseEvent: Flow<BaseViewModelContract.BaseEvent>
				get() = _baseEvent.receiveAsFlow()
				set(value) {}
		private var _baseEffect =
				MutableSharedFlow<BaseViewModelContract.BaseEffect>()
		override var baseEffect: SharedFlow<BaseViewModelContract.BaseEffect>
				get() = _baseEffect.asSharedFlow()
				set(value) {}

		init {
				eventHandler()
				stateHandler()
		}

		private fun stateHandler() {
				viewModelScope.launch {
						baseState.collectLatest { state ->
								when (state) {

										is BaseViewModelContract.BaseState.Success -> {
												val data = state.data as NewsModel
												if (searchNewsList.size == 0) {
														searchNewsList.clear()
														searchNewsList.addAll(data.results)
												} else {
														searchNewsList.addAll(data.results)
												}
												uiState.value = UiPagingState.Idle
												nextPage.value = data.nextPage
												canPaging.value = true
										}

										is BaseViewModelContract.BaseState.Loading -> {
												if (searchNewsList.size == 0) {
														uiState.value = UiPagingState.Loading
												} else {
														uiState.value = UiPagingState.PagingLoading
												}
												canPaging.value = false
										}

										is BaseViewModelContract.BaseState.Error -> {
												if (searchNewsList.size == 0) {
														uiState.value = UiPagingState.Error
												} else {
														uiState.value = UiPagingState.PagingError
												}
												canPaging.value = false
										}

										is BaseViewModelContract.BaseState.Empty -> {
												uiState.value = UiPagingState.Empty
												canPaging.value = false
										}

										else -> {}
								}
						}
				}
		}

		private fun eventHandler() {
				viewModelScope.launch {
						baseEvent.collectLatest { event ->
								when (event) {
										is BaseViewModelContract.BaseEvent.GetData -> {
												newsRepository.getNewsSearch(
														userSearch = userQuery.value,
														nextPage = nextPage.value,
												).collectLatest {
														_baseState.value = it
												}
										}
										is BaseViewModelContract.BaseEvent.ClearPaging -> {
												searchNewsList.clear()
												nextPage.value = ""
										}
										else -> {}
								}
						}
				}
		}

		override fun setBaseEvent(newsEvent: BaseViewModelContract.BaseEvent) {
				viewModelScope.launch {
						_baseEvent.send(newsEvent)
				}
		}

		override fun setBaseEffects(newsEffect: BaseViewModelContract.BaseEffect) {
				viewModelScope.launch {
						_baseEffect.emit(newsEffect)
				}
		}

}