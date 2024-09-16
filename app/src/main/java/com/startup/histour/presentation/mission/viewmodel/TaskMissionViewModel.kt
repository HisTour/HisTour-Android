package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.data.dto.mission.RequestQuizGrade
import com.startup.histour.domain.usecase.mission.GetMissionOfQuizUseCase
import com.startup.histour.domain.usecase.mission.GradeQuizUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskMissionViewModel @Inject constructor(
    getMissionOfQuizUseCase: GetMissionOfQuizUseCase,
    private val gradeQuizUseCase: GradeQuizUseCase
) : BaseViewModel() {

    private val _state = TaskMissionStateImpl()
    override val state: TaskMissionState = _state

    init {
        getMissionOfQuizUseCase.executeOnViewModel(
            params = "1",
            onMap = {
                val missions = it.quizzes ?: listOf()
                val subMissionType = it.missionType ?: "INTRO"
                _state.missionList.update { missions }
                _state.subMissionType.update { subMissionType }
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    fun checkAnswer(isLast: Boolean, taskId: Int, answer: String = "") {
        gradeQuizUseCase.executeOnViewModel(
            params = RequestQuizGrade(quizId = taskId, memberAnswer = answer, isLast),
            onMap = {
                _state.correctResponse.update { it }
                _state.moveEvent.update { isLast }
            },
            onEach = { it ->
            },
            onError = {

            }
        )
    }


}