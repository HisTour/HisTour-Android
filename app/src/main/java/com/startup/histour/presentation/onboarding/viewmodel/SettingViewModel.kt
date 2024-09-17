package com.startup.histour.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.usecase.auth.LogoutUseCase
import com.startup.histour.domain.usecase.auth.WithdrawalAccountUseCase
import com.startup.histour.domain.usecase.member.ChangeUserNickNameUseCase
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.model.UserInfoModel
import com.startup.histour.presentation.onboarding.model.NickNameChangedEvent
import com.startup.histour.presentation.onboarding.model.SettingViewMoveEvent
import com.startup.histour.presentation.onboarding.model.SettingViewState
import com.startup.histour.presentation.onboarding.model.SettingViewStateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    private val changeUserNickNameUseCase: ChangeUserNickNameUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawalAccountUseCase: WithdrawalAccountUseCase,
    private val getMyUserDataUseCase: GetMyUserDataUseCase
) : BaseViewModel() {
    private val _state = SettingViewStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        )
    )
    override val state: SettingViewState = _state

    fun changeUserNickName(name: String) {
        Log.e("LMH", "CHANGE NAME! $name")
        changeUserNickNameUseCase.executeOnViewModel(
            params = name,
            onEach = {
                fetchMyUserData()
                notifyEvent(NickNameChangedEvent.OnChangedNickName)
            },
            onError = {}
        )
    }

    fun withdrawalAccount() {
        Log.e("LMH", "MoveToLoginActivity")
        notifyEvent(SettingViewMoveEvent.MoveToLoginActivity)
         withdrawalAccountUseCase.executeOnViewModel(
             onEach = {
                 Log.e("LMH", "SUCCESS WITHDRAWAL")
                 viewModelScope.launch {
                     tokenDataStoreProvider.clearAllData()
                     userInfoDataStoreProvider.clearAllData()
                     // TODO MOVE LoginActivity
                     notifyEvent(SettingViewMoveEvent.MoveToLoginActivity)
                 }
             },
             onError = {

             }
         )
    }

    fun logout() {
        logoutUseCase.executeOnViewModel(
            onEach = {
                Log.e("LMH", "SUCCESS logoutUseCase")
                viewModelScope.launch {
                    tokenDataStoreProvider.clearAllData()
                    userInfoDataStoreProvider.clearAllData()
                    // TODO MOVE LoginActivity
                    notifyEvent(SettingViewMoveEvent.MoveToLoginActivity)
                }
            },
            onError = {
                Log.e("LMH", "ERROR logoutUseCase")
            }
        )
    }

    private fun fetchMyUserData() {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = {},
            onError = {}
        )
    }
}