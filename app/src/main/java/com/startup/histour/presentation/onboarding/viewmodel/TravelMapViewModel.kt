package com.startup.histour.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.usecase.place.GetTravelRegionsUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.onboarding.model.TravelMapViewState
import com.startup.histour.presentation.onboarding.model.TravelMapViewStateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelMapViewModel @Inject constructor(
    getTravelRegionsUseCase: GetTravelRegionsUseCase,
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider
) :
    BaseViewModel() {
    private val _state = TravelMapViewStateImpl()
    override val state: TravelMapViewState get() = _state

    init {
        getTravelRegionsUseCase.executeOnViewModel(
            onMap = {
                it.places?.map { place -> place.toPlace() } ?: emptyList()
            },
            onEach = {
                _state.placeList.update { it }
            },
            onError = {
                Log.e("LMH", "ERROR $it")
            }
        )
    }

    fun selectPlace(placeId: Int) {
        viewModelScope.launch {
            userInfoDataStoreProvider.setPlaceId(placeId.toString())
        }
    }
}