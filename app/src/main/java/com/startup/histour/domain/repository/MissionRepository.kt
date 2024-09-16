package com.startup.histour.domain.repository

import com.startup.histour.data.dto.mission.RequestQuizGrade
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.data.dto.mission.ResponseGradeQuizDto
import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    suspend fun getMissions(placeId: String): Flow<ResponseMissionDto>
    suspend fun clearMission(request:RequestUnlockMission): Flow<Boolean>
    suspend fun getQuiz(missionId: String): Flow<ResponseQuizDto>
    suspend fun gradeQuiz(requestQuizGrade: RequestQuizGrade): Flow<ResponseGradeQuizDto>
}