package com.startup.histour.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.data.util.NoCharacterException
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMyUserDataUseCase: GetMyUserDataUseCase,
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider
) : BaseViewModel() {
    override val state: State = object : State {}

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = {
                Log.e("LMH", "getMyUserDataUseCase Success")
                notifyEvent(SplashEvent.MoveMainActivity)
            },
            onError = {
                if (it is NoCharacterException) {
                    viewModelScope.launch {
                        userInfoDataStoreProvider.setPlaceId("-1")
                    }
                }
                notifyEvent(SplashEvent.MoveLoginActivity)
                Log.e("LMH", "getMyUserDataUseCase ERROR $it")
            }
        )
    }
}
