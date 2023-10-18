package com.example.newsapp.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.local.database.NewsRoomDatabase
import com.example.newsapp.local.api.LocalViewModelImp
import com.example.newsapp.local.mapper.EntityMapper
import com.example.newsapp.local.model.RoomEntity
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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocalViewModel(
		private var dataBase: NewsRoomDatabase,
		private var mapper: EntityMapper,
) : ViewModel(), BaseViewModelContract, LocalViewModelImp {

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
										else -> {}
								}
						}
				}
		}

		override fun getNewsRoomData() {
				viewModelScope.launch {
						_baseState.value = BaseViewModelContract.BaseState.Loading
						val list: List<RoomEntity> = onIO { dataBase.RoomDao().getAllNews() }
						if (list.isNotEmpty()) {
								_baseState.value =
										BaseViewModelContract.BaseState.Success(data = mapper.entityToArticle(list))
						} else {
								_baseState.value = BaseViewModelContract.BaseState.Empty
						}
				}
		}

		fun isArticleInDb(articleID: String): Boolean {
				var isSaved = false
				runBlocking {
						onIO {
								isSaved = dataBase.RoomDao().isArticleSaved(articleID = articleID)
						}
				}
				return isSaved
		}

		fun getAllArticleId(): List<String> {
				var list: List<String> = emptyList()
				runBlocking {
						onIO {
								list = dataBase.RoomDao().getArticleIdList()
						}
				}
				return list
		}

		override fun deleteAllItem() {
				runBlocking {
						onIO {
								dataBase.RoomDao().deleteAll()
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
								dataBase.RoomDao().insertNews(mapper.articleToRoomEntity(article))
						}
				}
		}

		override fun deleteItem(articleID: String) {
				viewModelScope.launch {
						onIO {
								dataBase.RoomDao().deleteNews(articleID = articleID)
						}
				}
		}


}


