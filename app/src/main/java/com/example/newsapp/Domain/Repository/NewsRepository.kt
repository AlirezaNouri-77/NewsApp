package com.example.newsapp.Domain.Repository

import com.example.newsapp.Domain.Model.BaseViewModelContract
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
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

