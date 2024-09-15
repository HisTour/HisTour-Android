package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseQuiz
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskMissionStateImpl : TaskMissionState {
    override val missionList = MutableStateFlow<List<ResponseQuiz>>(listOf())
}

interface TaskMissionState : State {
    val missionList: StateFlow<List<ResponseQuiz>>
}