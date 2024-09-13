package com.startup.histour.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.data.dto.auth.RequestLogin
import com.startup.histour.data.util.ClientException
import com.startup.histour.data.util.NoCharacterException
import com.startup.histour.domain.usecase.auth.LoginUseCase
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getMyUserDataUseCase: GetMyUserDataUseCase,
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider
) : BaseViewModel() {
    private val _state = LoginStateImpl()
    override val state: LoginState = _state
    fun login(type: String = "DEVELOPER", token: String = "asdasd") {
        loginUseCase.executeOnViewModel(
            params = RequestLogin.of(type, token),
            onEach = {
                if (it) {
                    fetchUserData()
                    Log.e("LMH", "loginUseCase Success")
//        테스트 시 아래 주석 풀고 fetchUserData 을 주석 하는 것도 추천드립니다. 근데 아마 메인에서도 데이터 없을 수도
//        notifyEvent(LoginViewEvent.MoveToMainView)
                } else {
                    Log.e("LMH", "loginUseCase Fail")
                }
            },
            onError = {
                Log.e("LMH", "loginUseCase ERROR")
            }
        )
    }

    private fun fetchUserData() {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = {
                Log.e("LMH", "getMyUserDataUseCase Success")
                userInfoOfMovingView()
            },
            onError = {
                if (it is NoCharacterException) {
                    viewModelScope.launch {
                        userInfoDataStoreProvider.setPlaceId("-1")
                    }
                    notifyEvent(LoginViewEvent.MoveToCharacterSettingView)
                }
                Log.e("LMH", "getMyUserDataUseCase ERROR $it")
            }
        )
    }

    private fun userInfoOfMovingView() {
        viewModelScope.launch {
            val userInfo = userInfoDataStoreProvider.getUserInfo()
            Log.e("LMH", "USER INFO $userInfo")
            if (userInfo.character.id == -1) {
                notifyEvent(LoginViewEvent.MoveToCharacterSettingView)
                return@launch
            }
            if (userInfo.placeId == -1) {
                notifyEvent(LoginViewEvent.MoveToPlaceSelectView)
                return@launch
            }
            notifyEvent(LoginViewEvent.MoveToMainView)
        }
    }
}
