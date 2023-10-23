package com.example.newsapp.local.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.local.database.NewsRoomDatabase
import com.example.newsapp.local.api.LocalViewModelImp
import com.example.newsapp.local.mapper.EntityMapper
import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.NewsEntity
import com.example.newsapp.local.model.SettingDataClass
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract
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
import kotlinx.coroutines.runBlocking

class LocalViewModel(
		private var database: NewsRoomDatabase,
) : ViewModel(), BaseViewModelContract, LocalViewModelImp {

		var articleIdList:List<String> = emptyList()
		private val entityMapper by lazy {
				EntityMapper()
		}

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
				getAllArticleId()
				getNewsRoomData()
				handlerEffects()
		}

		override fun handlerEffects() {
				viewModelScope.launch {
						baseEvent.collect {
								when (it) {
										is BaseViewModelContract.BaseEvent.GetData -> getNewsRoomData()
										is BaseViewModelContract.BaseEvent.InsertDataToDb -> insertItem(it.article)
										is BaseViewModelContract.BaseEvent.DeleteDataToDb -> deleteItem(it.articleID)
										is BaseViewModelContract.BaseEvent.DeleteAllDb -> deleteAllItem()
										is BaseViewModelContract.BaseEvent.UpdateReadState -> updateNewsReadState(newsIsRead = true, newsId = it.articleID)
										else -> {}
								}
						}
				}
		}

		override fun getNewsRoomData() {
				viewModelScope.launch {
						_baseState.value = BaseViewModelContract.BaseState.Loading
						val list: List<NewsEntity> = onIO { database.NewsDao().getAllNews() }
						if (list.isNotEmpty()) {
								_baseState.value =
										BaseViewModelContract.BaseState.Success(data = entityMapper.entityToArticle(list))
						} else {
								_baseState.value = BaseViewModelContract.BaseState.Empty("")
						}
				}
		}

		override fun updateNewsReadState(newsIsRead: Boolean, newsId: String) {
				// TODO
				viewModelScope.launch {
						onIO {
								database.NewsDao().setNewsIsRead(newsIsRead = newsIsRead , newsId = newsId)
						}
				}
		}

		fun getAllArticleId() {
				runBlocking {
						onIO {
								articleIdList = database.NewsDao().getArticleIdList()
						}
				}
		}

		override fun deleteAllItem() {
				runBlocking {
						onIO {
								database.NewsDao().deleteAll()
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


		override fun insertItem(article: Article) {
				viewModelScope.launch {
						onIO {
								database.NewsDao().insertNews(entityMapper.articleToRoomEntity(article))
						}
				}
		}

		override fun deleteItem(articleID: String) {
				viewModelScope.launch {
						onIO {
								database.NewsDao().deleteNews(articleID = articleID)
						}
				}
		}


}


