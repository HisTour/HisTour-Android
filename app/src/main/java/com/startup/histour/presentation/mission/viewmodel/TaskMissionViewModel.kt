package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.domain.usecase.mission.GetMissionOfQuizUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskMissionViewModel @Inject constructor(
    getMissionOfQuizUseCase: GetMissionOfQuizUseCase
) : BaseViewModel() {

    private val _state = TaskMissionStateImpl()
    override val state: TaskMissionState = _state

    init {
        getMissionOfQuizUseCase.executeOnViewModel(
            params = "1",
            onMap = {
                val missions = it.quizzes ?: listOf()
                _state.missionList.update { missions }
            },
            onEach = { it->
            },
            onError = {
            }
        )
    }

}