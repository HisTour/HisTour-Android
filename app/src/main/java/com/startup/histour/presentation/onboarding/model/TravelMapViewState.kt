package com.startup.histour.presentation.onboarding.model

import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TravelMapViewStateImpl(override val userInfo: StateFlow<UserInfoModel>) : TravelMapViewState {
    override val placeList = MutableStateFlow<List<Place>>(emptyList())

}

interface TravelMapViewState : State {
    val placeList: StateFlow<List<Place>>
    val userInfo: StateFlow<UserInfoModel>
}

sealed interface TravelMapViewEvent : BaseEvent {
    data object MoveToMainActivity : TravelMapViewEvent
    data class ShowToast(val msg: String) : TravelMapViewEvent
}