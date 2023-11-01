package com.example.newsapp.local.api

import com.example.newsapp.remote.model.Article

interface LocalViewModelImp {
		fun getNewsRoomData()
		fun insertItem (article: Article)
		fun deleteItem (articleID: String)
		fun getAllArticleId()
		fun deleteAllItem ()

		fun handlerEffects()
}