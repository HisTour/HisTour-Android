package com.startup.histour.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsControllerCompat
import com.startup.histour.presentation.login.viewmodel.SplashEvent
import com.startup.histour.presentation.login.viewmodel.SplashViewModel
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.util.extensions.collectFlow
import com.startup.histour.ui.theme.HistourTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 상태바를 투명하게 만들기
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        // 상태바 아이콘 색상을 제어할 수 있는 WindowInsetsController 생성
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true // 상태바 아이콘을 밝게 설정

        collectEvent()
        setContent {
            HistourTheme {
                SplashScreen()
            }
        }
    }

    private fun collectEvent() {
        collectFlow(splashViewModel.event.filterIsInstance<SplashEvent>()) {
            when (it) {
                SplashEvent.MoveMainActivity -> {
                    delay(2000)
                    Intent(this, MainActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }

                SplashEvent.MoveLoginActivity -> {
                    delay(2000)
                    Intent(this, LoginActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }
}