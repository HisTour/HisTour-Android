package com.startup.histour.presentation.util.extensions

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


/* 히스투어 스낵바, duration 은 Short 고정(4초) */
fun LifecycleOwner.showSnackBar(state: SnackbarHostState, message: String) {
    lifecycleScope.launch {
        state.showSnackbar(message = message)
    }
}