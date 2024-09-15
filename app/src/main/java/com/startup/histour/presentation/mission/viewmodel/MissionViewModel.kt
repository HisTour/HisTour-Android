package com.startup.histour.presentation.mission.viewmodel

import android.util.Log
import com.startup.histour.domain.usecase.mission.GetPlaceOfMissionUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    getPlaceOfMissionUseCase: GetPlaceOfMissionUseCase,
) :BaseViewModel() {

    private val _state = MissionStateImpl()
    override val state: MissionState = _state

    init {
        getPlaceOfMissionUseCase.executeOnViewModel(
            params = "1",
            onMap = {
                val missions = it.missions ?: listOf()
                val requiredMissionCount = it.requiredMissionCount ?: 1
                _state.missionList.update { missions }
                _state.requiredMissionCount.update{ requiredMissionCount }
            },
            onEach = { it->
            },
            onError = {
            }
        )
    }
}