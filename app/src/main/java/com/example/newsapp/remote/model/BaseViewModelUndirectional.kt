package com.example.newsapp.remote.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseViewModelUndirectional<STATE,EVENT,EFFECT> {
		var baseState:StateFlow<STATE>
		var baseEVENT:SharedFlow<EVENT>
		var baseEFFECT:Flow<EFFECT>
}