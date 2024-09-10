package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.auth.RequestLoginType
import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {
    @POST("auth")
    suspend fun login(
        @Body type: RequestLoginType,
        @Header("Authorization") authToken: String
    ): Response<BaseResponse<ResponseLoginDto>>
}