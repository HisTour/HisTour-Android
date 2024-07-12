package com.startup.histour.presentation.login.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.startup.histour.presentation.login.KaKaoLoginClient
import com.startup.histour.presentation.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var kaKaoLoginClient: KaKaoLoginClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 해당 로직들이 context가 필요해서 액티비티에서 작성
    private fun kakaoLogin() {
        lifecycleScope.launch {
            kaKaoLoginClient.login().fold(
                onSuccess = { token ->
                    Log.i("KakaoLogin", "$token")
                    // 성공 처리 로직
                },
                onFailure = { error ->
                    Log.e("KakaoLogin", "Login failed", error)
                    // 에러 처리
                }
            )
        }
    }

    private fun getKakaoInformation() {
        lifecycleScope.launch {
            kaKaoLoginClient.me().fold(
                onSuccess = { user ->
                    Log.i(
                        "KakaoUser", "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                    )
                },
                onFailure = {
                    //에러
                }
            )
        }
    }

}