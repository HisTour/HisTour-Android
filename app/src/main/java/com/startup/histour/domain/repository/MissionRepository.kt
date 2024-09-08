package com.startup.histour.domain.repository

import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    suspend fun getMissions(placeId: String): Flow<ResponseMissionDto>
    suspend fun getQuiz(missionId: String): Flow<ResponseQuizDto>
}