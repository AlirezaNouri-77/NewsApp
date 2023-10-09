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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

class NewsViewModel(
		private var newsRepository: NewsRepository
) : ViewModel(), BaseViewModelContract, NewsViewModelImp {

		var nextPage = mutableStateOf("")
		var newsMutableList: MutableList<Article> = mutableListOf()
		var canPaging = mutableStateOf(true)

		var newsListScrollState = mutableIntStateOf(0)
		var newsCategoryState = mutableIntStateOf(0)
		var newsCategory = mutableStateOf("Top")

		private var _baseState =
				MutableStateFlow<BaseViewModelContract.BaseState>(BaseViewModelContract.BaseState.Idle)
		override var baseState = _baseState.asStateFlow()

		private var _baseEvent =
				MutableSharedFlow<BaseViewModelContract.BaseEvent>()
		override var baseEVENT = _baseEvent.asSharedFlow()

		private var _baseEffect = Channel<BaseViewModelContract.BaseEffect>(Channel.UNLIMITED)
		override var baseEFFECT = _baseEffect.receiveAsFlow()

		init {
				viewModelScope.launch {
						newsRepository.getNews(category = newsCategory.value , page = nextPage.value)
				}
				handleEffects()
				handleState()
		}

		private fun handleState() {
				viewModelScope.launch {
						baseState.collectLatest { state ->

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
												_baseState.value = BaseViewModelContract.BaseState.Idle
										}

										is BaseViewModelContract.BaseState.Loading -> {
												canPaging.value = false
										}

										is BaseViewModelContract.BaseState.Error -> {
												_baseEvent.emit(BaseViewModelContract.BaseEvent.EventError(message = state.message))
												canPaging.value = true
										}

										else -> {}
								}
						}
				}
		}

		override fun handleEffects() {
				viewModelScope.launch {
						baseEFFECT.collect { state ->
								when (state) {
										is BaseViewModelContract.BaseEffect.GetData -> {
														newsRepository.getNews(
																category = state.userInput,
																page = state.page,
														).collect {
																_baseState.value = it
														}
										}
								}
						}
				}
		}

		fun clearPaging () {
				newsMutableList.clear()
				nextPage.value = ""
				newsListScrollState.intValue = 0
		}

		override fun setBaseEvent(newsEvent: BaseViewModelContract.BaseEvent) {
				viewModelScope.launch {
						_baseEvent.emit(newsEvent)
				}
		}

		override fun setBaseEffects(newEffect: BaseViewModelContract.BaseEffect) {
				viewModelScope.launch {
						_baseEffect.send(newEffect)
				}
		}

}
