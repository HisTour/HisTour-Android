package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.member.RequestUserCharacter
import com.startup.histour.data.dto.member.RequestUserNickName
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MemberApi {
    @PATCH("characters")
    suspend fun setUserCharacter(@Body requestUserCharacter: RequestUserCharacter): BaseResponse<Unit>

    @GET("members/info")
    suspend fun getUserInfo(): BaseResponse<ResponseUserInfoDto>

    @PATCH("members/info")
    suspend fun setUserNickName(@Body requestUserNickName: RequestUserNickName): BaseResponse<Unit>
}