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
		override var baseState: StateFlow<BaseViewModelContract.BaseState> = _baseState.asStateFlow()

		private var _baseEvent =
				MutableSharedFlow<BaseViewModelContract.BaseEvent>()
		override var baseEVENT: SharedFlow<BaseViewModelContract.BaseEvent> = _baseEvent.asSharedFlow()

		private var _baseEffects = Channel<BaseViewModelContract.BaseEffect>(Channel.UNLIMITED)
		override var baseEFFECT: Flow<BaseViewModelContract.BaseEffect> = _baseEffects.receiveAsFlow()

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
						baseEFFECT.collectLatest { stateEffect ->
								when (stateEffect) {
										is BaseViewModelContract.BaseEffect.GetData -> {
												getSearchNews(stateEffect.userInput)
										}
								}
						}
				}
		}

		override fun setBaseEvent(newsEvent: BaseViewModelContract.BaseEvent) {
				viewModelScope.launch {
						_baseEvent.emit(newsEvent)
				}
		}

		override fun setBaseEffects(newEffect: BaseViewModelContract.BaseEffect) {
				viewModelScope.launch {
						_baseEffects.send(newEffect)
				}
		}


}