package com.startup.histour.presentation.bundle.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.usecase.attraction.GetRecommendSpotUseCase
import com.startup.histour.domain.usecase.history.GetNationalHolidaysUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.bundle.model.BundleViewState
import com.startup.histour.presentation.bundle.model.BundleViewStateImpl
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BundleViewModel @Inject constructor(
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    private val getNationalHolidaysUseCase: GetNationalHolidaysUseCase,
    private val getRecommendSpotUseCase: GetRecommendSpotUseCase
) : BaseViewModel() {
    private val _state = BundleViewStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        )
    )
    override val state: BundleViewState
        get() = _state

    init {
        fetchAttraction()
        fetchHistoryHoliday()
    }

    fun fetchAttraction() {
        getRecommendSpotUseCase.executeOnViewModel(
            onMap = {
                it.attractions?.map { attraction -> attraction.toAttraction() } ?: emptyList()
            },
            onEach = { list ->

                _state.attractionList.update { list }
            },
            onError = {
                Log.e("LMH", "getRecommendSpotUseCase ERROR $it")
            }
        )
    }

    private fun fetchHistoryHoliday() {
        getNationalHolidaysUseCase.executeOnViewModel(
            onMap = {
                it.holidays?.map { holiday -> holiday.toHistoryHoliday() } ?: emptyList()
            },
            onEach = { list ->
                _state.historyHolidayList.update { list }
            },
            onError = {
                Log.e("LMH", "getNationalHolidaysUseCase ERROR $it")
            }
        )
    }
}