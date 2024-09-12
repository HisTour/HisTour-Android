package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.member.MemberDataSource
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val memberDataSource: MemberDataSource) : MemberRepository {
    override suspend fun setUserCharacter(characterId: Int) = memberDataSource.setUserCharacter(characterId)
    override suspend fun getUserInfo(): Flow<ResponseUserInfoDto> = flow { emit(memberDataSource.getUserInfo()) }
    override suspend fun setUserNickName(name: String) = memberDataSource.setUserNickName(name)
}