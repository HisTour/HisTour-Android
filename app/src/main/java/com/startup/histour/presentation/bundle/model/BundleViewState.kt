package com.startup.histour.presentation.bundle.model

import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BundleViewStateImpl(override val userInfo: StateFlow<UserInfoModel>) : BundleViewState {
    override val attractionList = MutableStateFlow<List<Attraction>>(emptyList())
    override val historyHolidayList = MutableStateFlow<List<HistoryHoliday>>(emptyList())
}

interface BundleViewState : State {
    val userInfo: StateFlow<UserInfoModel>
    val attractionList: StateFlow<List<Attraction>>
    val historyHolidayList: StateFlow<List<HistoryHoliday>>
}