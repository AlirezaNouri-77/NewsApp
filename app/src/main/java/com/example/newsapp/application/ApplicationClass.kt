package com.example.newsapp.application

import android.app.Application

class ApplicationClass : Application() {
  
  lateinit var diContainer: DiContainer
  
  override fun onCreate() {
	super.onCreate()
	diContainer = DiContainer(this)
  }
  
}