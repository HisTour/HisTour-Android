package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MissionClearStateImpl : MissionClearState {
    override val imageUrl = MutableStateFlow<String>("")
    override val missionList = MutableStateFlow<List<ResponseMission>>(listOf())
    override val clear = MutableStateFlow<Boolean>(false)
}

interface MissionClearState : State {
    val imageUrl: StateFlow<String>
    val missionList: StateFlow<List<ResponseMission>>
    val clear: StateFlow<Boolean>
}