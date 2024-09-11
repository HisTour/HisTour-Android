package com.startup.histour.presentation.main.viewmodel

import android.util.Log
import com.startup.histour.domain.usecase.member.ChangeCharacterOfUserUseCase
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getMyUserDataUseCase: GetMyUserDataUseCase, private val changeCharacterOfUserUseCase: ChangeCharacterOfUserUseCase) : BaseViewModel() {

    private val _state = HomeStateImpl()
    override val state: MainState = _state
    init {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = { response ->
                Log.e("LMH", "getMyUserDataUseCase SUCCESS $response")
            },
            onError = { error ->
                Log.e("LMH", "getMyUserDataUseCase FAIL $error")
            },
        )
    }
}

private class HomeStateImpl : MainState