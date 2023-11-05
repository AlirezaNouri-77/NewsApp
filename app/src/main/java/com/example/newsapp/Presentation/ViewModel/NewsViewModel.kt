package com.example.newsapp.Presentation.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Data.Repository.DatabaseRepository
import com.example.newsapp.Data.Repository.NewsRepository
import com.example.newsapp.Domain.Model.ActiveSettingSection
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.NewsModel
import com.example.newsapp.Domain.Model.SettingModel
import com.example.newsapp.Domain.Model.UiPagingState
import com.example.newsapp.Presentation.util.convertToString
import com.example.newsapp.Presentation.util.onIO
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
import java.util.Locale

class NewsViewModel(
		private var newsRepository: NewsRepository,
		private var databaseRepository: DatabaseRepository,
) : ViewModel(), BaseViewModelContract {

		var nextPage = mutableStateOf("")
		var newsMutableList: MutableList<Article> = mutableListOf()
		var canPaging = true

		var settingList: MutableList<SettingModel> = mutableStateListOf()
		var activeSection: MutableState<ActiveSettingSection> = mutableStateOf(
				ActiveSettingSection.Idle
		)

		var UiState = mutableStateOf(UiPagingState.Idle)

		var newsListScrollState = mutableIntStateOf(0)
		var newsCategoryState = mutableIntStateOf(0)
		var newsCategory = mutableStateOf("Top")

		private var _baseState =
				MutableStateFlow<BaseViewModelContract.BaseState>(BaseViewModelContract.BaseState.Idle)
		override var baseState: StateFlow<BaseViewModelContract.BaseState> = _baseState.asStateFlow()

		private var _baseEvent = Channel<BaseViewModelContract.BaseEvent>(Channel.UNLIMITED)
		override var baseEvent: Flow<BaseViewModelContract.BaseEvent> = _baseEvent.receiveAsFlow()

		private var _baseEffect =
				MutableSharedFlow<BaseViewModelContract.BaseEffect>()
		override var baseEffect: SharedFlow<BaseViewModelContract.BaseEffect> =
				_baseEffect.asSharedFlow()

		init {
				eventHandler()
				stateHandler()
				setBaseEvent(BaseViewModelContract.BaseEvent.GetSettings)
		}

		private fun stateHandler() {
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
												canPaging = true
												UiState.value = UiPagingState.Idle
										}

										is BaseViewModelContract.BaseState.Loading -> {
												UiState.value =
														if (newsMutableList.size == 0) UiPagingState.Loading else UiPagingState.PagingLoading
												canPaging = false
										}

										is BaseViewModelContract.BaseState.Empty -> {
												UiState.value = UiPagingState.Empty
												canPaging = false
										}

										is BaseViewModelContract.BaseState.Error -> {
												UiState.value =
														if (newsMutableList.size == 0) UiPagingState.Error else UiPagingState.PagingError
												canPaging = false
										}
										else -> {}
								}
						}
				}
		}

		private fun eventHandler() = viewModelScope.launch {
				baseEvent.collectLatest { state ->
						when (state) {

								is BaseViewModelContract.BaseEvent.GetData -> {
										newsRepository.getNews(
												category = newsCategory.value,
												nextPage = nextPage.value,
												settingCategory = activeSection.value.name.lowercase(Locale.getDefault()),
												settingQuery = settingList.convertToString(),
										).collectLatest {
												_baseState.value = it
										}
								}

								is BaseViewModelContract.BaseEvent.ClearPaging -> {
										newsMutableList.clear()
										nextPage.value = ""
										newsListScrollState.intValue = 0
								}

								is BaseViewModelContract.BaseEvent.GetSettings -> {
										databaseRepository.getSettings().collectLatest { setting ->
												activeSection.value = setting.first().activeSettingSection
												settingList = setting.first().settingList.toMutableStateList()
												setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
										}
								}

								is BaseViewModelContract.BaseEvent.UpdateSettings -> {
										onIO {
												databaseRepository.updateSetting(
														settingSection = state.category,
														settingEntity = state.setting,
												)
										}
								}

								else -> {}

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
