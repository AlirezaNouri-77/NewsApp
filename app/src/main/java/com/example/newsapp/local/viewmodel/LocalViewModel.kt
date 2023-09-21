package com.example.newsapp.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.local.database.NewsRoomDatabase
import com.example.newsapp.local.api.LocalViewModelImp
import com.example.newsapp.local.model.RoomEntity
import com.example.newsapp.remote.model.BaseViewModelContract
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.withContext

class LocalViewModel(
		var db: NewsRoomDatabase
) : ViewModel(), BaseViewModelContract, LocalViewModelImp {

		private var _baseState =
				MutableStateFlow<BaseViewModelContract.BaseState>(BaseViewModelContract.BaseState.Idle)
		override var baseState: StateFlow<BaseViewModelContract.BaseState>
				get() = _baseState.asStateFlow()
				set(value) {}

		private var _baseEvent =
				MutableSharedFlow<BaseViewModelContract.BaseEvent>()
		override var baseEVENT: SharedFlow<BaseViewModelContract.BaseEvent>
				get() = _baseEvent.asSharedFlow()
				set(value) {}

		private var _baseEffect = Channel<BaseViewModelContract.BaseEffect>(Channel.UNLIMITED)
		override var baseEFFECT: Flow<BaseViewModelContract.BaseEffect>
				get() = _baseEffect.receiveAsFlow()
				set(value) {}

		override fun handlerEffects() {
				viewModelScope.launch {
						baseEFFECT.collect {
								when (it) {
										is BaseViewModelContract.BaseEffect.GetData -> {
												getNewsRoomData()
										}
								}
						}
				}
		}

		override suspend fun getNewsRoomData() {

				_baseState.value = BaseViewModelContract.BaseState.Loading
				val list : List<RoomEntity> = onIO { db.RoomDao().getAllNews() }
				if (list.isNotEmpty()) {
						_baseState.value = BaseViewModelContract.BaseState.Success(list)
				} else {
						_baseState.value = BaseViewModelContract.BaseState.Empty
				}

		}

		override fun insertItem(roomEntity: RoomEntity) {
				viewModelScope.launch {
						onIO {
								db.RoomDao().insertNews(roomEntity)
						}
				}
		}

		override fun deleteItem(roomEntity: RoomEntity) {
				viewModelScope.launch {
						onIO {
								db.RoomDao().deleteNews(roomEntity)
						}
				}
		}

}

suspend inline fun <T> onIO(crossinline action: () -> T): T {
		return withContext(Dispatchers.IO) {
				action()
		}
}
