package com.startup.histour.presentation.util.extensions

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


/* 히스투어 스낵바, duration 은 Short 고정(4초) */
fun LifecycleOwner.showSnackBar(state: SnackbarHostState, message: String) {
    lifecycleScope.launch {
        state.showSnackbar(message = message)
    }
}

inline fun <T, R : LifecycleOwner> R.collectFlow(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) {
    when (this) {
        is ComponentActivity -> flow.flowWithLifecycle(lifecycle).onEach { block(it) }
            .launchIn(lifecycleScope)

        is Fragment -> flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { block(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        else -> {}
    }
}