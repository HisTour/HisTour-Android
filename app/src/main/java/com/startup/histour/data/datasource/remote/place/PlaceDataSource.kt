package com.startup.histour.data.datasource.remote.place

import com.startup.histour.data.dto.place.ResponsePlaceDto
import com.startup.histour.data.remote.api.PlaceApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject

class PlaceDataSource @Inject constructor(private val placeApi: PlaceApi) {
    suspend fun getPlaces(): ResponsePlaceDto {
        return handleExceptionIfNeed {
            placeApi.getPlaces().data
        }
    }
}