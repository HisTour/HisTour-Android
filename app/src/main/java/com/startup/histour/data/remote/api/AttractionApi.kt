package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.attraction.ResponseAttractionDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET

interface AttractionApi {
    @GET("attractions")
    suspend fun getAttractions(): BaseResponse<ResponseAttractionDto>
}