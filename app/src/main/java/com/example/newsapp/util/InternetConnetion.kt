package com.example.newsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import io.ktor.utils.io.errors.IOException
import okhttp3.Interceptor
import okhttp3.Response

class InternetConnectionInterceptor (var context: Context) : Interceptor {
		override fun intercept(chain: Interceptor.Chain): Response {
				val request = chain.request()
				if (!CheckInternet.checkNetworkConnection(context)){
						throw NoInternet
				}
				return chain.proceed(request)
		}
}

object NoInternet : IOException() {
		override val message: String
				get() {
						return "No connectivity"
				}
}

class CheckInternet {
		companion object {
				fun checkNetworkConnection(context: Context): Boolean {

						val connectivtymanager =
								context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

						val resultNetwork: Boolean
						val networkCapab = connectivtymanager.activeNetwork ?: return false
						val activeNetwork = connectivtymanager.getNetworkCapabilities(networkCapab) ?: return false

						resultNetwork = when {
								activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
								activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
								activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
								else -> {
										false
								}
						}
						return resultNetwork
				}

		}
}