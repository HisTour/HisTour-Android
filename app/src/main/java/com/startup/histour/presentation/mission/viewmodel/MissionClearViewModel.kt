package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.domain.usecase.mission.ClearSubMissionUseCase
import com.startup.histour.domain.usecase.mission.GetMissionOfQuizUseCase
import com.startup.histour.domain.usecase.mission.GetPlaceOfMissionUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MissionClearViewModel @Inject constructor(
    private val clearSubMissionUseCase: ClearSubMissionUseCase,
    getPlaceOfMissionUseCase: GetPlaceOfMissionUseCase,
    private val getMissionOfQuizUseCase: GetMissionOfQuizUseCase
) : BaseViewModel() {

    private val _state = MissionClearStateImpl()
    override val state: MissionClearState = _state

    init {
        getPlaceOfMissionUseCase.executeOnViewModel(
            params = "1",
            onMap = {
                val missions = it.missions ?: listOf()
                _state.missionList.update { missions }
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    fun clearSubMission(nextMissionId : Int){
        clearSubMissionUseCase.executeOnViewModel(
            params = "1" ,
            onMap = {
                if(it) choiceNextSubMission(nextMissionId)
            },
            onEach = { it ->
            },
            onError = {
            }
        )

    }

    private fun choiceNextSubMission(nextMissionId: Int){
        getMissionOfQuizUseCase.executeOnViewModel(
            params = nextMissionId.toString() ,
            onMap = {
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }


}