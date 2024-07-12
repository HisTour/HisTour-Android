package com.startup.histour.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
            kotlin.runCatching {
                kaKaoLoginClient.login()
            }.onSuccess {
                // OAuthToken 뜯어서 서버에 리퀘스트바디로 전달
                // DataStore or Encryptedsharedpreferences에 저장
                it.onSuccess { token ->
                    Log.i("KakaoLogin", "$token")
                }
            }
                .onFailure {
                    //에러
                }
        }
    }

    private fun getKakaoInformation() {
        lifecycleScope.launch {
            kotlin.runCatching {
                kaKaoLoginClient.me()
            }.onSuccess {
                it.onSuccess { user ->
                    Log.i(
                        "KakaoUser", "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                    )
                }
            }
                .onFailure {
                    //에러
                }
        }
    }

}