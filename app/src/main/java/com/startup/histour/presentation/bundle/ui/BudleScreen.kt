package com.startup.histour.presentation.bundle.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.startup.histour.presentation.navigation.MainScreens

@Composable
fun BundleScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(MainScreens.HistoryStory.route) }) {
            Text("HistoryStory")
        }
        Button(onClick = { navController.navigate(MainScreens.RecommendedSpot.route) }) {
            Text("RecommendedSpot")
        }
    }
}