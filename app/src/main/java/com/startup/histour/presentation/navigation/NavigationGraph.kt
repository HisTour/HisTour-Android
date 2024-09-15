package com.startup.histour.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.startup.histour.presentation.bundle.model.Attraction
import com.startup.histour.presentation.bundle.ui.BundleScreen
import com.startup.histour.presentation.bundle.ui.HistoryStoryScreen
import com.startup.histour.presentation.bundle.ui.RecommendedSpotScreen
import com.startup.histour.presentation.login.ui.LoginScreen
import com.startup.histour.presentation.main.ui.BottomNavigationScreen
import com.startup.histour.presentation.main.ui.CharacterSettingScreen
import com.startup.histour.presentation.main.ui.HomeMissionScreen
import com.startup.histour.presentation.mission.ui.CameraScreen
import com.startup.histour.presentation.mission.ui.GptScreen
import com.startup.histour.presentation.mission.ui.MissionClearScreen
import com.startup.histour.presentation.mission.ui.MissionMapScreen
import com.startup.histour.presentation.mission.ui.TaskMissionScreen
import com.startup.histour.presentation.mission.ui.SubMissionChoiceScreen
import com.startup.histour.presentation.onboarding.ui.CharacterScreen
import com.startup.histour.presentation.onboarding.ui.NickNameChangeScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingMapScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingScreen
import com.startup.histour.presentation.onboarding.ui.SettingScreen
import com.startup.histour.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.startup.histour.presentation.widget.snack.HistourSnackBar
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun LoginNavigationGraph(viewModel: OnBoardingViewModel) {

    val navController = rememberNavController()
    val snackBarHostState = SnackbarHostState()
    Scaffold(
        snackbarHost = {
            HistourSnackBar(snackBarHostState = snackBarHostState)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(HistourTheme.colors.white000)
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            NavHost(
                navController = navController,
                startDestination = LoginScreens.Login.route
            ) {
                composable(LoginScreens.Login.route) { LoginScreen(navController) }
                composable(LoginScreens.OnBoarding.route) { OnBoardingScreen(navController) }
                composable(LoginScreens.Setting.route) { SettingScreen(navController) }
                composable(LoginScreens.Character.route) { CharacterScreen(navController) }
                composable(LoginScreens.Map.route) { OnBoardingMapScreen(navController, snackBarHostState) }
            }
        }
    }
}

@Composable
fun MainNavigationGraph() {

    val navController = rememberNavController()
    val snackBarHostState = SnackbarHostState()

    Scaffold(
        snackbarHost = {
            HistourSnackBar(snackBarHostState = snackBarHostState)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(HistourTheme.colors.white000)
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            NavHost(
                navController = navController,
                startDestination = MainScreens.BottomNavigation.route
            ) {
                composable(MainScreens.BottomNavigation.route) { BottomNavigationScreen(navController) }
                composable(MainScreens.Home.route) { HomeMissionScreen(navController) }
                composable(MainScreens.MissionMap.route) { MissionMapScreen(navController) }
                composable(MainScreens.MissionTask.route) { TaskMissionScreen(navController) }
                composable(MainScreens.SubMissionChoice.route) { SubMissionChoiceScreen(navController) }
                composable(MainScreens.Camera.route) { CameraScreen(navController) }
                composable(MainScreens.GPT.route) { GptScreen(navController) }
                composable(MainScreens.Bundle.route) { BundleScreen(navController) }
//                composable(MainScreens.HistoryStory.route) { HistoryStoryScreen(navController) }
                composable(
                    route = MainScreens.RecommendedSpot.route + "/{attraction}",
                    arguments = listOf(navArgument("attraction") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val attractionJson = Uri.decode(navBackStackEntry.arguments?.getString("attraction"))
                    val attraction = Gson().fromJson(attractionJson, Attraction::class.java)
                    RecommendedSpotScreen(navController, attraction)
                }
                composable(MainScreens.Character.route) { CharacterScreen(navController) }
                composable(MainScreens.CharacterSetting.route) { CharacterSettingScreen(navController) }
                composable(MainScreens.Setting.route) { SettingScreen(navController) }
                composable(MainScreens.NickNameChange.route) { NickNameChangeScreen(navController) }
                composable(MainScreens.MissionClear.route) { MissionClearScreen(navController) }
                composable(MainScreens.Map.route) { OnBoardingMapScreen(navController, snackBarHostState) }
            }
        }
    }
}


