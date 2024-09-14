package com.startup.histour.data.datasource.remote.mission

import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import com.startup.histour.data.remote.api.MissionApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject

class MissionDataSource @Inject constructor(private val missionApi: MissionApi) {
    suspend fun getMissions(placeId: String): ResponseMissionDto {
        return handleExceptionIfNeed {
            missionApi.getMissions(placeId).data
        }
    }

    suspend fun clearSubMission(placeId: String): Boolean{
        return handleExceptionIfNeed {
            missionApi.clearSubMission(placeId).data
        }
    }

    suspend fun getQuiz(missionId: String): ResponseQuizDto {
        return handleExceptionIfNeed {
            missionApi.getQuiz(missionId).data
        }
    }
}