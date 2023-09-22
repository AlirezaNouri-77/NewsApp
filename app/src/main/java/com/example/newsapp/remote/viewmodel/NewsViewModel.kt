package com.example.newsapp.remote.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.remote.api.NewsViewModelImp
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsViewModel(
		private var newsRepository: NewsRepository
) : ViewModel(), BaseViewModelContract, NewsViewModelImp {

		private var _baseState =
				MutableStateFlow<BaseViewModelContract.BaseState>(BaseViewModelContract.BaseState.Idle)
		override var baseState = _baseState.asStateFlow()

		private var _baseEvent =
				MutableSharedFlow<BaseViewModelContract.BaseEvent>()
		override var baseEVENT = _baseEvent.asSharedFlow()

		private var _baseEffect = Channel<BaseViewModelContract.BaseEffect>(Channel.UNLIMITED)
		override var baseEFFECT = _baseEffect.receiveAsFlow()

		var newsListScrollState = mutableIntStateOf(0)
		var newsCategoryState = mutableIntStateOf(0)

		init {
				handleEffects()
		}

//		override fun getNews(category: String) {
//				viewModelScope.launch {
//						newsRepository.getNews(category = category).collect {
//								_baseState.value = it
//						}
//				}
//		}

		override fun handleEffects() {
				viewModelScope.launch {
						_baseEffect.receiveAsFlow().collectLatest { state ->
								when (state) {
										is BaseViewModelContract.BaseEffect.GetData -> {
												newsRepository.getNews(state.category).collect {
														_baseState.value = it
												}
										}
								}
						}
				}
		}

//		override fun readStateNews() {
//				viewModelScope.launch {
////						newsRepository.getNews().collect {
////								_baseState.value = it
////						}
//				}
//		}

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

