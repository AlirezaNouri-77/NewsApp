package com.example.newsapp.remote.repository

import android.util.Log
import com.example.newsapp.local.viewmodel.onIO
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.remote.api.NewsRepositoryImp
import com.example.newsapp.remote.model.BaseViewModelContract
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NewsRepository(
		private var KtorClient: HttpClient
) : NewsRepositoryImp {

		override suspend fun getNews(): Flow<BaseViewModelContract.BaseState> {
				return flow {
						this.emit(BaseViewModelContract.BaseState.Loading)
						val response =
								KtorClient.get("https://newsapi.org/v2/top-headlines?country=us&apiKey=6aea97c3195747368196f8a5acaa2343")
						if (response.status.isSuccess()) {
								Log.d("TAG", "response: " + response.status)
								this.emit(BaseViewModelContract.BaseState.Success(response.body() as NewsModel))
						} else {
								this.emit(BaseViewModelContract.BaseState.Error)
						}
				}
		}

}