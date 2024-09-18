package com.startup.histour.presentation.mission.viewmodel

import androidx.lifecycle.viewModelScope
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.domain.usecase.mission.GetPlaceOfMissionUseCase
import com.startup.histour.domain.usecase.mission.UnlockAndChoiceSubMissionUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubMissionChoiceViewModel @Inject constructor(
    private val unlockAndChoiceSubMissionUseCase: UnlockAndChoiceSubMissionUseCase,
    private val getPlaceOfMissionUseCase: GetPlaceOfMissionUseCase,
) : BaseViewModel() {

    private val _state = SubMissionChoiceStateImpl()
    override val state: SubMissionChoiceState = _state

    fun getNextSubmissionList(placeId: Int = 1) {
        getPlaceOfMissionUseCase.executeOnViewModel(
            params = placeId.toString(),
            onMap = {
                val imageUrl = it.selectMissionImageUrl
                _state.imageUrl.update { imageUrl ?: "" }
                val missions = it.missions ?: listOf()
                _state.missionList.update { missions }
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    fun completeAndChoiceNextMission(completeMissionId: Int, nextMissionId: Int) {
        unlockAndChoiceSubMissionUseCase.executeOnViewModel(
            params = RequestUnlockMission(
                completedMissionId = completeMissionId,
                nextMissionId = nextMissionId
            ),
            onMap = {
                viewModelScope.launch {
                    notifyEvent(SubMissionChoiceEvent.MoveToMissionMap)
                    return@launch
                }
            },
            onEach = {},
            onError = {}
        )
    }
}