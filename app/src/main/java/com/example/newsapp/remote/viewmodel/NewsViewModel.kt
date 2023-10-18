package com.example.newsapp.remote.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.remote.api.NewsViewModelImp
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsViewModel(
		private var newsRepository: NewsRepository
) : ViewModel(), BaseViewModelContract, NewsViewModelImp {

		var nextPage = mutableStateOf("")
		var newsMutableList: MutableList<Article> = mutableListOf()
		var canPaging = mutableStateOf(true)
		var isPaging = mutableStateOf(false)

		var newsListScrollState = mutableIntStateOf(0)
		var newsCategoryState = mutableIntStateOf(0)
		var newsCategory = mutableStateOf("Top")


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
				viewModelScope.launch {
						newsRepository.getNews(category = newsCategory.value , page = nextPage.value)
				}
				handleEffects()
				handleState()
		}

		override fun handleState() {
				viewModelScope.launch {
						baseState.collect { state ->
								when (state) {
										is BaseViewModelContract.BaseState.Success -> {
												val data = (state.data as NewsModel)
												if (nextPage.value.isEmpty()) {
														newsMutableList.clear()
														newsMutableList.addAll(data.results)
												} else {
														newsMutableList.addAll(data.results)
												}
												nextPage.value = data.nextPage
												canPaging.value = true
												isPaging.value = false
												_baseState.value = BaseViewModelContract.BaseState.Idle
										}
										is BaseViewModelContract.BaseState.Loading -> {
												canPaging.value = false
												isPaging.value = true
										}
										is BaseViewModelContract.BaseState.Error -> {
												_baseState.emit(BaseViewModelContract.BaseState.Error(""))
												canPaging.value = false
												isPaging.value = false
										}
										else -> {}
								}
						}
				}
		}

		override fun handleEffects() {
				viewModelScope.launch {
						baseEvent.collect { state ->
								when (state) {
										is BaseViewModelContract.BaseEvent.GetData -> {
														newsRepository.getNews(
																category = newsCategory.value,
																page = nextPage.value,
														).distinctUntilChanged().collectLatest {
																_baseState.value = it
														}
										}
										else -> {}
								}
						}
				}
		}

		override fun clearPaging () {
				newsMutableList.clear()
				nextPage.value = ""
				newsListScrollState.intValue = 0
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
