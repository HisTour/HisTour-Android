package com.startup.histour.presentation.login.viewmodel

import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _state = LoginStateImpl()
    override val state: LoginState = _state

}

private class LoginStateImpl : LoginState