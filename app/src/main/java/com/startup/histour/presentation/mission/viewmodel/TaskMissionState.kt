package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseQuiz
import com.startup.histour.data.dto.mission.ResponseQuizCorrectDto
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskMissionStateImpl : TaskMissionState {
    override val missionList = MutableStateFlow<List<ResponseQuiz>>(listOf())
    override val subMissionType = MutableStateFlow<String>("")
    override val moveEvent = MutableStateFlow<Boolean>(false)
    override val correctResponse =
        MutableStateFlow<ResponseQuizCorrectDto>(ResponseQuizCorrectDto(false, 0, 0))
}

interface TaskMissionState : State {
    val missionList: StateFlow<List<ResponseQuiz>>
    val subMissionType: StateFlow<String>
    val moveEvent: StateFlow<Boolean>
    val correctResponse: StateFlow<ResponseQuizCorrectDto>
}