package com.example.newsapp.remote.api

import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryImp {
		suspend fun getNews(category: String, page: String): Flow<BaseViewModelContract.BaseState>
		suspend fun getNewsSearch(userSearch: String): Flow<BaseViewModelContract.BaseState>

}

