package com.startup.histour.presentation.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.startup.histour.presentation.navigation.MainNavigationGraph
import com.startup.histour.ui.theme.HistourTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistourTheme {
                MainNavigationGraph()
            }
        }
    }
}