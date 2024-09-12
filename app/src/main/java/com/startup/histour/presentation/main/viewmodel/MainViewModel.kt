package com.startup.histour.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.domain.usecase.place.GetMyCurrentTravelPlaceUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyUserDataUseCase: GetMyUserDataUseCase,
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    private val getMyCurrentTravelPlaceUseCase: GetMyCurrentTravelPlaceUseCase
) : BaseViewModel() {
    private val _state = HomeStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        ),
    )
    override val state: HomeState = _state

    init {
        getMyUserDataUseCase.executeOnViewModel(
            onEach = { response ->
                Log.e("LMH", "getMyUserDataUseCase SUCCESS $response")
            },
            onError = { error ->
                Log.e("LMH", "getMyUserDataUseCase FAIL $error")
            },
        )
        viewModelScope.launch {
            getMyCurrentTravelPlaceUseCase.executeOnViewModel(
                params = userInfoDataStoreProvider.getPlaceId(),
                onEach = {
                    _state.place.update { it }
                },
                onError = {
                    Log.e("LMH", "getMyCurrentTravelPlaceUseCase FAIL $it")
                }
            )
        }
    }
}
