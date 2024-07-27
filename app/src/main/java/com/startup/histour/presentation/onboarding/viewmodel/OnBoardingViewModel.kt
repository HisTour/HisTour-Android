package com.startup.histour.presentation.onboarding.viewmodel

import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : BaseViewModel() {

    private val _state = OnBoardingStateImpl()
    override val state: OnBoardingState = _state

    fun onTutorialFinished() {
        _state.valid.update { true }
    }

    fun onTutorialStarted() {
        _state.valid.update { false }
    }

}

private class OnBoardingStateImpl : OnBoardingState {
    override val valid = MutableStateFlow(false)
}