package com.startup.histour.data.dto.auth

data class RequestLogin private constructor(val type: RequestLoginType, val token: String) {
    companion object {
        fun of(type: String, token: String): RequestLogin = RequestLogin(RequestLoginType(type), "Bearer $token")
    }
}
