package com.startup.histour.presentation.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.startup.histour.presentation.bundle.ui.BundleScreen
import com.startup.histour.presentation.mission.ui.MissionMapScreen
import com.startup.histour.presentation.navigation.MainScreens

@Composable
fun BottomNavigationScreen(navController: NavController) {

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    selected = currentRoute == MainScreens.MissionMap.route,
                    onClick = { bottomNavController.navigate(MainScreens.MissionMap.route) },
                    label = { Text("미션 지도") },
                    icon = { Icon(Icons.Default.Place, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = currentRoute == MainScreens.Home.route,
                    onClick = { bottomNavController.navigate(MainScreens.Home.route) },
                    label = { Text("홈") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = currentRoute == MainScreens.Bundle.route,
                    onClick = { bottomNavController.navigate(MainScreens.Bundle.route) },
                    label = { Text("보따리") },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(bottomNavController, startDestination = MainScreens.Home.route, Modifier.padding(innerPadding)) {
            composable(MainScreens.MissionMap.route) { MissionMapScreen(navController) }
            composable(MainScreens.Home.route) { HomeMissionScreen(navController) }
            composable(MainScreens.Bundle.route) { BundleScreen(navController) }
        }
    }



}