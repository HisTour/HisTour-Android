package com.startup.histour.presentation.login.viewmodel

import com.startup.histour.presentation.base.BaseEvent

sealed interface SplashEvent : BaseEvent {
    data object MoveMainActivity : SplashEvent
    data object MoveLoginActivity : SplashEvent
}