package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.domain.usecase.mission.GetMissionOfQuizUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskMissionViewModel @Inject constructor(
    getMissionOfQuizUseCase: GetMissionOfQuizUseCase
) : BaseViewModel() {

    private val _state = TaskMissionStateImpl()
    override val state: TaskMissionState = _state

    private val _moveEvent = MutableStateFlow<Boolean>(false)
    val moveEvent = _moveEvent.asStateFlow()

    init {
        getMissionOfQuizUseCase.executeOnViewModel(
            params = "1",
            onMap = {
                val missions = it.quizzes ?: listOf()
                val subMissionType = it.missionType ?: "INTRO"
                _state.missionList.update { missions }
                _state.subMissionType.update { subMissionType }
            },
            onEach = { it->
            },
            onError = {
            }
        )
    }

//    private fun checkAnswer(isLast:Boolean, quizId:Int){
//        useCase.executeOnViewModel(
//            params = "1",
//            onMap = {
//                if(isLast == true) moveEvent.value = true
//                    _state.correctResponse.update {it}
//            },
//            onEach = { it->
//            },
//            onError = {
//            }
//
//    }


}