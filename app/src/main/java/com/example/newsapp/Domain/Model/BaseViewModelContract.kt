package com.example.newsapp.Domain.Model

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
	data class EventError(var message: String) : BaseEvent()
	
  }
  
  sealed class BaseEvent {
	data class GetData(
	  val userInput: String = "",
	  val page: String = "",
	) : BaseEvent()
	
	data class InsertNews(var article: Article) : BaseEvent()
	data class UpdateSettings(
	  var category: ActiveSettingSection = ActiveSettingSection.IDLE,
	  var setting: List<SettingModel> = emptyList()
	) : BaseEvent()
	
	data object GetSettings : BaseEvent()
	data class DeleteNews(var articleID: String) : BaseEvent()
	data object GetArticleId : BaseEvent()
	data object DeleteAllNews : BaseEvent()
	data object ClearPaging : BaseEvent()
	
  }
  
}