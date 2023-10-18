package com.example.newsapp.remote.api

import com.example.newsapp.remote.model.BaseViewModelContract
import kotlinx.coroutines.flow.StateFlow

interface NewsSearchImpl {
		fun readSearchNewsFlow(): StateFlow<BaseViewModelContract.BaseState>
		fun getSearchNews(userSearch: String)
		fun handleEffect()

}