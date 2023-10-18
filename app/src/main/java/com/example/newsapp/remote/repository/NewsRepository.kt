package com.example.newsapp.remote.repository

import android.util.Log
import com.example.newsapp.constant.constant
import com.example.newsapp.constant.constant.API_KEY
import com.example.newsapp.constant.constant.API_KEY2
import com.example.newsapp.constant.constant.BASE_URL
import com.example.newsapp.remote.api.NewsRepositoryImp
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.model.NewsModel
import com.example.newsapp.util.NoInternet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.net.ssl.HttpsURLConnection

class NewsRepository(
		private var ktorClient: HttpClient
) : NewsRepositoryImp {
		override suspend fun getNews(
				category: String,
				page: String,
		): Flow<BaseViewModelContract.BaseState> {

				return flow {
						this.emit(BaseViewModelContract.BaseState.Loading)
						try {
								val response =
										ktorClient.get {
												url {
														protocol = URLProtocol.HTTPS
														host = BASE_URL
														appendPathSegments("api", "1", "news")
														parameters.append("category", category)
														parameters.append("language", "en")
														if (page.isNotEmpty()) {
																parameters.append("page", page)
														}
														parameters.append("full_content", "1")
														parameters.append("apiKey", API_KEY2)
												}
										}
								Log.d("URL", "getNews: " + response.body())
								this.emit(BaseViewModelContract.BaseState.Success(data = response.body() as NewsModel))
						} catch (e: NoInternet) {
								Log.d("TAG3242", "intercept: " + "ss")
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						} catch (e: SocketTimeoutException) {
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						}
				}.flowOn(Dispatchers.IO)
		}

		override suspend fun getNewsSearch(userSearch: String): Flow<BaseViewModelContract.BaseState> {
				return flow {
						//emit(BaseViewModelContract.BaseState.Loading)
						val response = ktorClient.get {
								url {
										protocol = URLProtocol.HTTPS
										host = BASE_URL
										appendPathSegments("api", "1", "news")
										parameters.append("q", userSearch)
										parameters.append("language", "en")
										parameters.append("apiKey", API_KEY2)
								}
						}

						Log.d("URL", "getNewsSearch: " + response.body())

						if (response.status.isSuccess()) {
								emit(BaseViewModelContract.BaseState.Success(response.body() as NewsModel))
						} else {
								//emit(BaseViewModelContract.BaseState.Error(BaseViewModelContract.BaseEvent.EventError))
						}
				}
		}
}


