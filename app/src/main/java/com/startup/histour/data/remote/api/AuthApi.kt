package com.startup.histour.data.remote.api

import com.startup.histour.data.util.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.PATCH

interface AuthApi {

    @DELETE("auth")
    suspend fun withdrawalAccount(): BaseResponse<Unit>

    @PATCH("auth")
    suspend fun logout(): BaseResponse<Unit>
}