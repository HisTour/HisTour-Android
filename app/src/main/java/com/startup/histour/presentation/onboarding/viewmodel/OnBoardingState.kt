package com.startup.histour.presentation.onboarding.viewmodel

import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.StateFlow

interface OnBoardingState : State {
    val valid: StateFlow<Boolean>
}