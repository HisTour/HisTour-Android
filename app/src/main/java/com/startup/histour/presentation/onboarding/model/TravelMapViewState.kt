package com.startup.histour.presentation.onboarding.model

import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TravelMapViewStateImpl: TravelMapViewState {
    override val placeList =  MutableStateFlow<List<Place>>(emptyList())

}

interface TravelMapViewState : State {
    val placeList : StateFlow<List<Place>>
}