package com.example.newsapp.remote.model

interface BaseViewModelContract :
		BaseViewModelBlueprint<BaseViewModelContract.BaseState, BaseViewModelContract.BaseEvent, BaseViewModelContract.BaseEffect> {

		sealed class BaseState {
				data object Idle : BaseState()
				data object Loading : BaseState()
				data class Success(var data: Any) : BaseState()
				data class Error(var message: String) : BaseState()
				data class Empty(var message: String) : BaseState()
		}

		sealed class BaseEffect {
				data class EventError (var message : String) : BaseEvent()

		}

		sealed class BaseEvent {
				data class GetData(var userInput: String = "" , var page:String = "") : BaseEvent()
				data class InsertDataToDb(var article: Article) : BaseEvent()
				data object InsertDataToSettingDb : BaseEvent()
				data class DeleteDataToDb(var articleID: String) : BaseEvent()
				data class UpdateReadState(var articleID: String) : BaseEvent()
				data object DeleteAllDb : BaseEvent()
		}

}