package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("auth")
    suspend fun login(@Body type: String): BaseResponse<ResponseLoginDto>
}