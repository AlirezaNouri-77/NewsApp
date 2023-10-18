package com.example.newsapp.local.api

import com.example.newsapp.local.model.RoomEntity
import com.example.newsapp.remote.model.Article
import com.example.newsapp.remote.model.BaseViewModelContract
import kotlinx.coroutines.flow.StateFlow

interface LocalViewModelImp {
		fun getNewsRoomData()
		fun insertItem (article: Article)
		fun deleteItem (articleID: String)
		fun deleteAllItem ()
		fun handlerEffects()
}