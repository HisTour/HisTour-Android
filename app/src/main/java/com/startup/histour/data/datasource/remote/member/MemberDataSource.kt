package com.startup.histour.data.datasource.remote.member

import com.startup.histour.data.dto.member.RequestUserCharacter
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.data.remote.api.MemberApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject

class MemberDataSource @Inject constructor(private val memberApi: MemberApi) {

    suspend fun setUserCharacter(characterId: Int) {
        return handleExceptionIfNeed {
            memberApi.setUserCharacter(RequestUserCharacter(characterId))
        }
    }

    suspend fun getUserInfo(): ResponseUserInfoDto {
        return handleExceptionIfNeed {
            memberApi.getUserInfo().data
        }
    }
}