package com.example.newsapp.Data.Repository

import android.util.Log
import com.example.newsapp.constant.constant.API_KEY2
import com.example.newsapp.constant.constant.BASE_URL
import com.example.newsapp.Domain.Repository.NewsRepository
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.NewsModel
import com.example.newsapp.Presentation.util.NoInternet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InterruptedIOException

class NewsRepository(
		private var ktorClient: HttpClient,
) : NewsRepository {
		override fun getNews(
				category: String,
				settingQuery: String,
				settingCategory: String,
				nextPage: String,
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
														parameters.append(settingCategory, settingQuery)
														if (nextPage.isNotEmpty()) {
																parameters.append("page", nextPage)
														}
														parameters.append("full_content", "1")
														parameters.append("apiKey", API_KEY2)
												}
										}
								Log.d("URL", "getNews: " + response.body())
								val data = response.body() as NewsModel
								if (data.totalResults != 0) {
										this.emit(BaseViewModelContract.BaseState.Success(data = response.body() as NewsModel))
								} else {
										this.emit(
												BaseViewModelContract.BaseState.Empty(
														message = "Nothing found change your settings and try again"
												)
										)
								}
						} catch (e: NoInternet) {
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						} catch (e: InterruptedIOException) {
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						}
				}
		}

		override fun getNewsSearch(
				userSearch: String,
				nextPage: String,
		): Flow<BaseViewModelContract.BaseState> {
				return flow {
						emit(BaseViewModelContract.BaseState.Loading)
						try {
								val response = ktorClient.get {
										url {
												protocol = URLProtocol.HTTPS
												host = BASE_URL
												appendPathSegments("api", "1", "news")
												parameters.append("q", userSearch)
												if (nextPage.isNotEmpty()) {
														parameters.append("page", nextPage)
												}
												parameters.append("language", "en")
												parameters.append("apiKey", API_KEY2)
										}
								}
								Log.d("URL", "getSearchNews: " + response.body())
								val data = response.body() as NewsModel
								if (data.totalResults != 0) {
										this.emit(BaseViewModelContract.BaseState.Success(data = response.body() as NewsModel))
								} else {
										this.emit(
												BaseViewModelContract.BaseState.Empty(
														message = "Nothing found change your settings and try again"
												)
										)
								}
						} catch (e: NoInternet) {
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						} catch (e: InterruptedIOException) {
								this.emit(
										BaseViewModelContract.BaseState.Error(
												message = e.toString()
										)
								)
						}

				}
		}
}


