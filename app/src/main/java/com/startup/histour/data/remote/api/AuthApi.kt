package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("auth")
    suspend fun login(@Body type: String): BaseResponse<ResponseLoginDto>

    @DELETE("auth")
    suspend fun withdrawalAccount(): BaseResponse<Unit>

    @PATCH("auth")
    suspend fun logout(): BaseResponse<Unit>

    @GET("auth/token")
    suspend fun alignRefreshToken(): BaseResponse<ResponseLoginDto>
}