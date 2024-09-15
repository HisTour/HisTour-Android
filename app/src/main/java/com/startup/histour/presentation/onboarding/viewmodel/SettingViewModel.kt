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
import com.startup.histour.presentation.onboarding.model.SettingViewMoveEvent
import com.startup.histour.presentation.onboarding.model.SettingViewState
import com.startup.histour.presentation.onboarding.model.SettingViewStateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
    private val _state = SettingViewStateImpl()
    override val state: SettingViewState = _state

    init {
        collectUserInfo()
    }

    fun changeUserNickName(name: String) {
        Log.e("LMH", "CHANGE NAME! $name")
        changeUserNickNameUseCase.executeOnViewModel(
            params = name,
            onEach = {
                fetchMyUserData()
            },
            onError = {}
        )
    }

    fun withdrawalAccount() {
        Log.e("LMH", "MoveToLoginActivity")
        notifyEvent(SettingViewMoveEvent.MoveToLoginActivity)
        /* withdrawalAccountUseCase.executeOnViewModel(
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
         )*/
    }

    fun logout() {
        logoutUseCase.executeOnViewModel(
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

    private fun collectUserInfo() {
        viewModelScope.launch {
            userInfoDataStoreProvider.getUserInfoFlow().collect { userData ->
                _state.userInfo.update { userData }
            }
        }
    }

    private fun fetchMyUserData() {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = { userData ->
                _state.userInfo.update { userData }
            },
            onError = {}
        )
    }
}