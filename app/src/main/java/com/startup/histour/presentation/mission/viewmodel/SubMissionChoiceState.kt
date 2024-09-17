package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SubMissionChoiceStateImpl : SubMissionChoiceState {
    override val imageUrl = MutableStateFlow<String>("")
    override val missionList = MutableStateFlow<List<ResponseMission>>(listOf())
}

interface SubMissionChoiceState : State {
    val imageUrl: StateFlow<String>
    val missionList: StateFlow<List<ResponseMission>>
}

sealed interface SubMissionChoiceEvent : BaseEvent {
    data object MoveToMissionMap : SubMissionChoiceEvent
}