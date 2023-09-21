package com.example.newsapp.local.api

import com.example.newsapp.local.model.RoomEntity
import com.example.newsapp.remote.model.BaseViewModelContract
import kotlinx.coroutines.flow.StateFlow

interface LocalViewModelImp {
		suspend fun getNewsRoomData()
		fun insertItem (roomEntity: RoomEntity)
		fun deleteItem (roomEntity: RoomEntity)
		fun handlerEffects()
}