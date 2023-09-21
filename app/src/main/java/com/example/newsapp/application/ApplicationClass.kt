package com.example.newsapp.application

import android.app.Application
import com.example.newsapp.AppDependencyContainer

class ApplicationClass : Application() {

		lateinit var appDependencyContainer: AppDependencyContainer

		override fun onCreate() {
				super.onCreate()
				appDependencyContainer = AppDependencyContainer(this)
		}

}