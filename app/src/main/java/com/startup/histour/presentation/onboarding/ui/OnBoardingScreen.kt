package com.startup.histour.presentation.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.startup.histour.presentation.navigation.LoginScreens

@Composable
fun OnBoardingScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(LoginScreens.Character.route) }) {
            Text("Character")
        }

        Button(onClick = { navController.navigate(LoginScreens.Setting.route) }) {
            Text("Settings")
        }
        Button(onClick = { navController.navigate(LoginScreens.Map.route) }) {
            Text("Map")
        }
    }
}