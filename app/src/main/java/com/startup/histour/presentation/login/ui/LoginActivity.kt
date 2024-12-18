package com.startup.histour.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.startup.histour.presentation.login.KaKaoLoginClient
import com.startup.histour.presentation.login.viewmodel.LoginViewEvent
import com.startup.histour.presentation.login.viewmodel.LoginViewModel
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.navigation.LoginNavigationGraph
import com.startup.histour.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.startup.histour.ui.theme.HistourTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()

    @Inject
    lateinit var kaKaoLoginClient: KaKaoLoginClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 상태바를 투명하게 만들기
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        // 상태바 아이콘 색상을 제어할 수 있는 WindowInsetsController 생성
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true // 상태바 아이콘을 밝게 설정

        setContent {
            HistourTheme {
                val navigateToMain by onBoardingViewModel.state.valid.collectAsState()
                LaunchedEffect(navigateToMain) {
                    if (navigateToMain) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        onBoardingViewModel.onTutorialStarted()
                    }
                }
                LaunchedEffect(Unit) {
                    loginViewModel.event.collectLatest {
                        when (it) {
                            is LoginViewEvent.ClickKakaoLogin -> kakaoLogin()
                        }
                    }
                }
                LoginNavigationGraph(viewModel = onBoardingViewModel)
            }
        }
    }

    // 해당 로직들이 context가 필요해서 액티비티에서 작성
    private fun kakaoLogin() {
        lifecycleScope.launch {
            kaKaoLoginClient.login().fold(
                onSuccess = { token ->
                    loginViewModel.login("KAKAO", token.accessToken)
                },
                onFailure = { error ->
                    Log.e("KakaoLogin", "Login failed", error)
                    // 에러 처리
                }
            )
        }
    }
//
//    private fun getKakaoInformation() {
//        lifecycleScope.launch {
//            kaKaoLoginClient.me().fold(
//                onSuccess = { user ->
//                    Log.i(
//                        "KakaoUser", "사용자 정보 요청 성공" +
//                                "\n회원번호: ${user.id}" +
//                                "\n이메일: ${user.kakaoAccount?.email}" +
//                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
//                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
//                    )
//                },
//                onFailure = {
//                    //에러
//                }
//            )
//        }
//    }

}