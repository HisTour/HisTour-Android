package com.startup.histour.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

abstract class BaseActivity<UE : UiEvent, NE : NavigationEvent> : ComponentActivity() {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    private fun handleErrorEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.filterIsInstance<BaseEvent.NotHandlingExceptionDelivery>().collect {
                    Toast.makeText(this@BaseActivity, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    abstract fun handleUiEvent(uiEvent: UE)

    abstract fun handleNavigationEvent(navigationEventFlow: Flow<NE>)

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}