package com.startup.histour.presentation.main.viewmodel

import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import com.startup.histour.presentation.onboarding.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeStateImpl(override val userInfo: StateFlow<UserInfoModel>) : HomeState {
    override val place= MutableStateFlow<Place>(Place.orEmpty())
}

interface HomeState : State {
    val userInfo: StateFlow<UserInfoModel>
    val place: StateFlow<Place>
}