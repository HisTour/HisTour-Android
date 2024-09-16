package com.startup.histour.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.startup.histour.presentation.login.viewmodel.SplashEvent
import com.startup.histour.presentation.login.viewmodel.SplashViewModel
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.util.extensions.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterIsInstance

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectEvent()
    }

    private fun collectEvent() {
        collectFlow(splashViewModel.event.filterIsInstance<SplashEvent>()) {
            when (it) {
                SplashEvent.MoveMainActivity -> {
                    Intent(this, MainActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }

                SplashEvent.MoveLoginActivity -> {
                    Intent(this, LoginActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }
}