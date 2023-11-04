package com.example.newsapp.Presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Domain.Entity.NewsEntity
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Repository.LocalRepository
import com.example.newsapp.Domain.mapper.ModelMapper
import com.example.newsapp.Presentation.util.onIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LocalViewModel(
		private var databaseRepository: LocalRepository,
		private var modelMapper: ModelMapper,
) : ViewModel(), BaseViewModelContract {

		var articleIdList: MutableList<String> = mutableListOf()

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
		}

		private fun eventHandler() {
				viewModelScope.launch {
						baseEvent.collect {
								when (it) {

										is BaseViewModelContract.BaseEvent.GetData -> {
												_baseState.value = BaseViewModelContract.BaseState.Loading
												val list: List<NewsEntity> = onIO { databaseRepository.getAllNews() }
												if (list.isNotEmpty()) {
														_baseState.value =
																BaseViewModelContract.BaseState.Success(
																		data = modelMapper.entityToArticle(
																				list
																		)
																)
												} else {
														_baseState.value = BaseViewModelContract.BaseState.Empty("")
												}
										}

										is BaseViewModelContract.BaseEvent.InsertNews -> {
												onIO {
														databaseRepository.insertNews(it.article)
												}
										}

										is BaseViewModelContract.BaseEvent.DeleteNews -> {
												onIO {
														databaseRepository.deleteNews(it.articleID)
												}
										}

										is BaseViewModelContract.BaseEvent.DeleteAllNews -> {
												onIO {
														databaseRepository.deleteAllNews()
												}
										}

										is BaseViewModelContract.BaseEvent.GetArticleId -> {
												onIO {
														articleIdList.clear()
														articleIdList.addAll(databaseRepository.getAllArticleId())
												}
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

