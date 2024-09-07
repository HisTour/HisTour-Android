package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MissionApi {
    @GET("mission/place/{placeId}")
    suspend fun getMissions(@Path("placeId") placeId: String): BaseResponse<ResponseMissionDto>

    @GET("quiz/mission/{missionId}")
    suspend fun getQuiz(@Path("missionId") missionId: String): BaseResponse<ResponseQuizDto>
}