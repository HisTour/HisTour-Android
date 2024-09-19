package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.ResponseQuiz
import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskMissionStateImpl : TaskMissionState {
    override val subMissionName = MutableStateFlow<String>("")
    override val quizzesList = MutableStateFlow<List<ResponseQuiz>>(listOf())
    override val subMissionType = MutableStateFlow<String>("")
}

interface TaskMissionState : State {
    val subMissionName: StateFlow<String>
    val quizzesList: StateFlow<List<ResponseQuiz>>
    val subMissionType: StateFlow<String>
}

sealed interface TaskMissionEvent : BaseEvent {
    data class MoveToClearScreen(val clearType: String, val subMissionType:String, val requiredCount:Int, val completedCount:Int) : TaskMissionEvent
    data class ShowToast(val msg: String) : TaskMissionEvent
    data object MoveToNextPage: TaskMissionEvent
}