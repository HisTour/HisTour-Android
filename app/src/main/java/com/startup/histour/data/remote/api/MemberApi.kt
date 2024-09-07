package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.member.RequestUserCharacter
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MemberApi {
    @PATCH("characters")
    suspend fun setUserCharacter(requestUserCharacter: RequestUserCharacter): BaseResponse<Unit>

    @GET("info")
    suspend fun getUserInfo(): BaseResponse<ResponseUserInfoDto>
}