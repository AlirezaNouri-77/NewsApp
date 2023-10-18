package com.example.newsapp.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.remote.api.NewsSearchImpl
import com.example.newsapp.remote.model.BaseViewModelContract
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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsSearchViewModel(
		private var newsRepository: NewsRepository
) : ViewModel(), BaseViewModelContract, NewsSearchImpl {

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
				handleEffect()
		}

		override fun readSearchNewsFlow(): StateFlow<BaseViewModelContract.BaseState> {
				return baseState
		}

		override fun getSearchNews(userSearch: String) {
				viewModelScope.launch {
						newsRepository.getNewsSearch(userSearch).collect { state ->
								_baseState.value = state
						}
				}
		}

		override fun handleEffect() {
				viewModelScope.launch {
						baseEvent.collectLatest { stateEffect ->
								when (stateEffect) {
										is BaseViewModelContract.BaseEvent.GetData -> {
												getSearchNews(stateEffect.userInput)
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