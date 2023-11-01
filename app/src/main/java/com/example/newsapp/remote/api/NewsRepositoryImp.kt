package com.example.newsapp.remote.api

import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryImp {
		fun getNews(
				category: String,
				settingQuery: String,
				settingCategory: String,
				nextPage: String,
		): Flow<BaseViewModelContract.BaseState>

		fun getNewsSearch(
				userSearch: String,
				nextPage: String,
		): Flow<BaseViewModelContract.BaseState>

}

