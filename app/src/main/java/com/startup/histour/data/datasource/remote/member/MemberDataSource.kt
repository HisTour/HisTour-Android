package com.startup.histour.data.datasource.remote.member

import android.util.Log
import com.startup.histour.data.dto.member.RequestUserCharacter
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.data.remote.api.MemberApi
import com.startup.histour.data.util.NoCharacterException
import com.startup.histour.data.util.UnknownException
import com.startup.histour.data.util.handleExceptionIfNeed
import org.json.JSONObject
import javax.inject.Inject

class MemberDataSource @Inject constructor(private val memberApi: MemberApi) {

    suspend fun setUserProfile(characterId: Int, userName: String) {
        return handleExceptionIfNeed {
            memberApi.setUserProfile(RequestUserCharacter(characterId, userName))
        }
    }

    suspend fun getUserInfo(): ResponseUserInfoDto {
        return handleExceptionIfNeed {
            val response = memberApi.getUserInfo()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                responseBody.data
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val errorJson = JSONObject(errorBody)
                Log.e("LMH", "RESPONSE $errorBody")
                // code 값이 NO_CHARACTER 인지 확인
                val code = errorJson.getString("code")
                if (code == "NO_CHARACTER") {
                    val message = errorJson.getString("message")
                    println("Code: $code, Message: $message")
                    throw NoCharacterException(message)
                } else {
                    throw UnknownException()
                }
            }
        }
    }
}