package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.place.RequestRecommendPlace
import com.startup.histour.data.dto.place.ResponsePlaceDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceApi {
    @GET("places")
    suspend fun getPlaces(): BaseResponse<ResponsePlaceDto>

    @POST("places/recommend")
    suspend fun postRecommendPlace(@Body requestRecommendPlace: RequestRecommendPlace): Response<BaseResponse<Unit>>
}