package com.example.newsapp.Domain.Model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseViewModelBlueprint<STATE, EVENT, EFFECT> {
  var baseState: StateFlow<STATE>
  var baseEvent: Flow<EVENT>
  var baseEffect: SharedFlow<EFFECT>
  fun setBaseEvent(newsEvent: BaseViewModelContract.BaseEvent)
  fun setBaseEffects(newsEffect: BaseViewModelContract.BaseEffect)
}