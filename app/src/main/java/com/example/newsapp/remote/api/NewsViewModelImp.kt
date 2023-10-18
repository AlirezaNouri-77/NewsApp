package com.example.newsapp.remote.api

import com.example.newsapp.remote.model.BaseViewModelContract

interface NewsViewModelImp {
		fun handleEffects()
		fun clearPaging()
		fun handleState()

}