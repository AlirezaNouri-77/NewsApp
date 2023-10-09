package com.example.newsapp.remote.model

interface BaseViewModelContract :
		BaseViewModelUndirectional<BaseViewModelContract.BaseState, BaseViewModelContract.BaseEvent, BaseViewModelContract.BaseEffect> {

		sealed class BaseState {
				data object Idle : BaseState()
				data object Loading : BaseState()
				data class Success(var data: Any) : BaseState()
				data class Error(var message: String) : BaseState()
				data object Empty : BaseState()
		}

		sealed class BaseEvent {
				data class EventError (var message : String) : BaseEvent()
				data object Idle : BaseEvent()

		}

		sealed class BaseEffect {
				data class GetData(var userInput: String , var page:String) : BaseEffect()
		}

}