package com.startup.histour.presentation.mission.viewmodel

import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.domain.usecase.mission.UnlockAndChoiceSubMissionUseCase
import com.startup.histour.domain.usecase.mission.GetPlaceOfMissionUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.mission.util.MissionValues.FINAL_MISSION_ID
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionClearViewModel @Inject constructor(
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    private val unlockAndChoiceSubMissionUseCase: UnlockAndChoiceSubMissionUseCase,
) : BaseViewModel() {

    private val _state = MissionClearStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        )
    )
    override val state: MissionClearState = _state

    fun choiceFinalSubMission(completeMissionId: Int, nextMissionId: Int = FINAL_MISSION_ID) {
        unlockAndChoiceSubMissionUseCase.executeOnViewModel(
            params = RequestUnlockMission(
                completedMissionId = completeMissionId,
                nextMissionId = nextMissionId
            ),
            onMap = {
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    fun clearFinalMission() {
        unlockAndChoiceSubMissionUseCase.executeOnViewModel(
            params = RequestUnlockMission(
                completedMissionId = FINAL_MISSION_ID,
            ),
            onMap = {
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }
}