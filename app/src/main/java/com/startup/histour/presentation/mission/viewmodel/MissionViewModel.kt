package com.startup.histour.presentation.mission.viewmodel

import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.domain.usecase.mission.GetPlaceOfMissionUseCase
import com.startup.histour.domain.usecase.mission.UnlockAndChoiceSubMissionUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.mission.util.MissionValues.COMPLETE_STATE
import com.startup.histour.presentation.mission.util.MissionValues.PROGRESS_STATE
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    private val getPlaceOfMissionUseCase: GetPlaceOfMissionUseCase,
    private val unlockAndChoiceSubMissionUseCase: UnlockAndChoiceSubMissionUseCase,
) : BaseViewModel() {

    private val _state = MissionStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        )
    )
    override val state: MissionState = _state

    fun getSubMissionList(placeId: Int) {
        getPlaceOfMissionUseCase.executeOnViewModel(
            params = placeId.toString(),
            onMap = {
                val missions = it.missions ?: listOf()
                val requiredMissionCount = it.requiredMissionCount ?: 1
                _state.missionList.update { missions }
                if (_state.missionList.value.filter { it.state == COMPLETE_STATE || it.state == PROGRESS_STATE }
                        .isEmpty()) {
                    unLockIntroMission()
                }
                _state.requiredMissionCount.update { requiredMissionCount }
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    private fun unLockIntroMission() {
        // intro missionid 1로 고정함..
        unlockAndChoiceSubMissionUseCase.executeOnViewModel(
            params = RequestUnlockMission(nextMissionId = 1),
            onMap = { getSubMissionList(1) },
            onEach = {},
            onError = {}
        )
    }
}