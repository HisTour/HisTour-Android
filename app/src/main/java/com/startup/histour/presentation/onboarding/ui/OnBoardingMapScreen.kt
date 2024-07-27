package com.startup.histour.presentation.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.startup.histour.presentation.navigation.LoginScreens
import com.startup.histour.presentation.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun OnBoardingMapScreen(navController: NavController, viewModel: OnBoardingViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { viewModel.onTutorialFinished() }) {
            Text("Tutorial Finish")
        }
    }
}