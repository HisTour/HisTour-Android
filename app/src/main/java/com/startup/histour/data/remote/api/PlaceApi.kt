package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.place.ResponsePlaceDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET

interface PlaceApi {
    @GET("places")
    suspend fun getPlaces(): BaseResponse<ResponsePlaceDto>
}