package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.mission.RequestQuizGrade
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.data.dto.mission.ResponseGradeQuizDto
import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MissionApi {
    @GET("missions/place/{placeId}")
    suspend fun getMissions(@Path("placeId") placeId: String): BaseResponse<ResponseMissionDto>

    @PATCH("missions/unlock")
    suspend fun clearSubMission(@Body requestUnlockMission: RequestUnlockMission): BaseResponse<Boolean>

    @GET("quizzes/mission/{missionId}")
    suspend fun getQuiz(@Path("missionId") missionId: String): BaseResponse<ResponseQuizDto>

    @POST("quizzes/grade")
    suspend fun gradeQuiz(@Body requestQuizGrade: RequestQuizGrade): BaseResponse<ResponseGradeQuizDto>
}