package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.member.RequestUserCharacter
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MemberApi {
    @PATCH("members/profile")
    suspend fun setUserProfile(@Body requestUserCharacter: RequestUserCharacter): BaseResponse<Unit>

    @GET("members/info")
    suspend fun getUserInfo(): Response<BaseResponse<ResponseUserInfoDto>>
}