package com.startup.histour.presentation.login.viewmodel

import android.util.Log
import com.startup.histour.data.dto.auth.RequestLogin
import com.startup.histour.domain.usecase.auth.LoginUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {
    private val _state = LoginStateImpl()
    override val state: LoginState = _state
    fun login(type: String = "DEVELOPER", token: String = "asdasd") {
        loginUseCase.executeOnViewModel(
            params = RequestLogin.of(type, token),
            onEach = {
                if (it) {
                    Log.e("LMH", "Success")
                } else {
                    Log.e("LMH", "Fail")
                }
                // Success
            },
            onError = {
                Log.e("LMH", "Fail")
                // fail
            }
        )
    }
}

private class LoginStateImpl : LoginState