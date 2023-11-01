package com.example.newsapp.remote.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.local.database.NewsRoomDatabase
import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.SettingDataClass
import com.example.newsapp.local.model.SettingListDataClass
import com.example.newsapp.remote.api.NewsViewModelImp
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.repository.NewsRepository
import com.example.newsapp.util.convertToString
import com.example.newsapp.util.onIO
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
		private var database: NewsRoomDatabase,
) : ViewModel(), BaseViewModelContract, NewsViewModelImp {

		var nextPage = mutableStateOf("")
		var newsMutableList: MutableList<Article> = mutableListOf()
		var canPaging = true

		var UiState = mutableStateOf(UiPagingState.Idle)

		var newsListScrollState = mutableIntStateOf(0)
		var newsCategoryState = mutableIntStateOf(0)
		var newsCategory = mutableStateOf("Top")

		var settingList: SnapshotStateList<SettingDataClass> = mutableStateListOf()
		var activeSection: MutableState<ActiveSettingSectionEnum> = mutableStateOf(
				ActiveSettingSectionEnum.Idle
		)

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
				handleEffects()
				handleState()
				provideSetting()
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

		override fun handleEffects() {
				viewModelScope.launch {
						baseEvent.collect { state ->
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

										is BaseViewModelContract.BaseEvent.InsertDataToSettingDb -> {
												viewModelScope.launch {
														onIO {
																database.SettingDao().updateSettings(
																		settingSection = activeSection.value,
																		settingEntity = SettingListDataClass(list = settingList.toList()),
																)
														}
												}
										}

										else -> {}
								}
						}
				}
		}

		private fun provideSetting() {
				viewModelScope.launch {
						database.SettingDao().getLanguageSettingList().collect { list ->
								activeSection.value = list.first().activeSettingSection
								settingList = list.first().settingList.list.toMutableStateList()
								setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
						}
				}
		}

		override fun clearPaging() {
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

enum class UiPagingState {
		Loading, PagingLoading, Error, PagingError, Idle, Empty
}
