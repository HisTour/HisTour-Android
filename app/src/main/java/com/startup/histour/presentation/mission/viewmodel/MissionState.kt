package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MissionStateImpl(override val userInfo: StateFlow<UserInfoModel>) : MissionState {
    override val missionList = MutableStateFlow<List<ResponseMission>>(listOf())
    override val requiredMissionCount = MutableStateFlow(1)
}

interface MissionState : State {
    val missionList: StateFlow<List<ResponseMission>>
    val requiredMissionCount: StateFlow<Int>
    val userInfo: StateFlow<UserInfoModel>
}