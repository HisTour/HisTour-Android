package com.startup.histour.data.datasource.remote.place

import com.startup.histour.data.dto.place.RequestRecommendPlace
import com.startup.histour.data.dto.place.ResponsePlaceDto
import com.startup.histour.data.remote.api.PlaceApi
import com.startup.histour.data.util.handleExceptionIfNeed
import retrofit2.http.Body
import javax.inject.Inject

class PlaceDataSource @Inject constructor(private val placeApi: PlaceApi) {
    suspend fun getPlaces(): ResponsePlaceDto {
        return handleExceptionIfNeed {
            placeApi.getPlaces().data
        }
    }

    suspend fun postRecommendPlace(content: String): Boolean {
        return handleExceptionIfNeed {
            val response = placeApi.postRecommendPlace(RequestRecommendPlace(content))
            response.code() == 200
        }
    }
}