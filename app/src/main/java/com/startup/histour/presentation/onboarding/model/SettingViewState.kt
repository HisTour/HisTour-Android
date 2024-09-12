package com.startup.histour.presentation.onboarding.model

import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingViewStateImpl : SettingViewState {
    override val userInfo = MutableStateFlow<UserInfoModel>(UserInfoModel.orEmpty())
}

interface SettingViewState : State {
    val userInfo: StateFlow<UserInfoModel>
}

sealed interface SettingViewMoveEvent : BaseEvent {
    data object None : SettingViewMoveEvent
    data object MoveToLoginActivity : SettingViewMoveEvent
}