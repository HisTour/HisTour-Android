package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MissionStateImpl : MissionState {
    override val missionList = MutableStateFlow<List<ResponseMission>>(listOf())
}

interface MissionState : State {
    val missionList: StateFlow<List<ResponseMission>>
}