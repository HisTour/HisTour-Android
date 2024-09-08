package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET

interface TokenUpdateApi {
    @GET("auth/token")
    suspend fun alignRefreshToken(): BaseResponse<ResponseLoginDto>
}