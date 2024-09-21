package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.sse.RequestGetUrl
import com.startup.histour.data.dto.sse.ResponseGetUrlDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("assistant")
    suspend fun getUrl(@Body body: RequestGetUrl): BaseResponse<ResponseGetUrlDto>
}