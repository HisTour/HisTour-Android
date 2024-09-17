package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MissionClearStateImpl(override val userInfo: StateFlow<UserInfoModel>) : MissionClearState {
    override val clear = MutableStateFlow<Boolean>(false)
}

interface MissionClearState : State {
    val clear: StateFlow<Boolean>
    val userInfo: StateFlow<UserInfoModel>
}