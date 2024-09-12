package com.startup.histour.presentation.bundle.model

import com.startup.histour.presentation.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BundleViewStateImpl : BundleViewState {
    override val attractionList = MutableStateFlow<List<Attraction>>(emptyList())
    override val historyHolidayList = MutableStateFlow<List<HistoryHoliday>>(emptyList())
}

interface BundleViewState : State {
    val attractionList: StateFlow<List<Attraction>>
    val historyHolidayList: StateFlow<List<HistoryHoliday>>
}