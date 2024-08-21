package com.startup.histour.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.startup.histour.presentation.bundle.ui.BundleScreen
import com.startup.histour.presentation.bundle.ui.HistoryStoryScreen
import com.startup.histour.presentation.bundle.ui.RecommendedSpotScreen
import com.startup.histour.presentation.login.ui.LoginScreen
import com.startup.histour.presentation.main.ui.BottomNavigationScreen
import com.startup.histour.presentation.main.ui.CharacterSettingScreen
import com.startup.histour.presentation.main.ui.HomeScreen
import com.startup.histour.presentation.mission.ui.CameraScreen
import com.startup.histour.presentation.mission.ui.GptScreen
import com.startup.histour.presentation.mission.ui.MissionMapScreen
import com.startup.histour.presentation.mission.ui.MissionScreen
import com.startup.histour.presentation.onboarding.ui.CharacterScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingMapScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingScreen
import com.startup.histour.presentation.onboarding.ui.SettingScreen
import com.startup.histour.presentation.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun LoginNavigationGraph(viewModel: OnBoardingViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginScreens.Login.route
    ) {
        composable(LoginScreens.Login.route) { LoginScreen(navController) }
        composable(LoginScreens.OnBoarding.route) { OnBoardingScreen(navController) }
        composable(LoginScreens.Setting.route) { SettingScreen(navController) }
        composable(LoginScreens.Character.route) { CharacterScreen(navController) }
        composable(LoginScreens.Map.route) { _ ->
            OnBoardingMapScreen(navController, viewModel = viewModel)
        }
    }
}

@Composable
fun MainNavigationGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainScreens.BottomNavigation.route
    ) {
        composable(MainScreens.BottomNavigation.route) { BottomNavigationScreen(navController) }
        composable(MainScreens.Home.route) { HomeScreen(navController) }
        composable(MainScreens.MissionMap.route) { MissionMapScreen(navController) }
        composable(MainScreens.Mission.route) { MissionScreen(navController) }
        composable(MainScreens.Camera.route) { CameraScreen(navController) }
        composable(MainScreens.GPT.route) { GptScreen(navController) }
        composable(MainScreens.Bundle.route) { BundleScreen(navController) }
        composable(MainScreens.HistoryStory.route) { HistoryStoryScreen(navController) }
        composable(MainScreens.RecommendedSpot.route) { RecommendedSpotScreen(navController) }
        composable(MainScreens.Character.route) { CharacterScreen(navController) }
        composable(MainScreens.CharacterSetting.route) { CharacterSettingScreen(navController) }
        composable(MainScreens.Setting.route) { SettingScreen(navController) }
    }
}


