package com.startup.histour.presentation.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.startup.histour.presentation.navigation.MainNavigationGraph
import com.startup.histour.ui.theme.HistourTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 상태바를 투명하게 만들기
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        // 상태바 아이콘 색상을 제어할 수 있는 WindowInsetsController 생성
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true // 상태바 아이콘을 밝게 설정

        setContent {
            HistourTheme {
                MainNavigationGraph()
            }
        }
    }
}