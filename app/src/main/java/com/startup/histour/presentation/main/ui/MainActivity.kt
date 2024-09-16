package com.startup.histour.presentation.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.startup.histour.presentation.navigation.MainNavigationGraph
import com.startup.histour.ui.theme.HistourTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistourTheme {
                MainNavigationGraph()
            }
        }
    }
}