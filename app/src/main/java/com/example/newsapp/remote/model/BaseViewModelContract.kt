package com.example.newsapp.remote.model

interface BaseViewModelContract :
		BaseViewModelUndirectional<BaseViewModelContract.BaseState, BaseViewModelContract.BaseEvent, BaseViewModelContract.BaseEffect> {

		sealed class BaseState{
				data object Idle : BaseState()
				data object Loading : BaseState()
				data class Success(var data: Any) : BaseState()
				data object Error : BaseState()
				data object Empty:BaseState()
		}

		sealed class BaseEvent {
				data object Message : BaseEvent()
				data object Error : BaseEvent()
		}

		sealed class BaseEffect {
				data object GetData : BaseEffect()
		}

}