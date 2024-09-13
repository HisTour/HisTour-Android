package com.startup.histour.presentation.login.viewmodel

import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginState : State {
    val userInfo: StateFlow<UserInfoModel>
}

class LoginStateImpl : LoginState {
    override val userInfo = MutableStateFlow<UserInfoModel>(UserInfoModel.orEmpty())
}

sealed interface LoginViewEvent : BaseEvent {
    data object MoveToCharacterSettingView : LoginViewEvent
    data object MoveToPlaceSelectView : LoginViewEvent
    data object MoveToMainView : LoginViewEvent
}