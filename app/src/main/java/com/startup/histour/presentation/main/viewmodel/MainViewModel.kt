package com.startup.histour.presentation.main.viewmodel

import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _state = HomeStateImpl()
    override val state: MainState = _state

}

private class HomeStateImpl : MainState