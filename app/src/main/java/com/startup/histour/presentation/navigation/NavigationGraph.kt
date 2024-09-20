package com.startup.histour.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.startup.histour.presentation.bundle.model.Attraction
import com.startup.histour.presentation.bundle.ui.BundleScreen
import com.startup.histour.presentation.bundle.ui.RecommendedSpotScreen
import com.startup.histour.presentation.login.ui.LoginScreen
import com.startup.histour.presentation.main.ui.BottomNavigationScreen
import com.startup.histour.presentation.main.ui.CharacterSettingScreen
import com.startup.histour.presentation.main.ui.HomeMissionScreen
import com.startup.histour.presentation.mission.ui.CameraScreen
import com.startup.histour.presentation.mission.ui.GptScreen
import com.startup.histour.presentation.mission.ui.MissionClearScreen
import com.startup.histour.presentation.mission.ui.MissionMapScreen
import com.startup.histour.presentation.mission.ui.SubMissionChoiceScreen
import com.startup.histour.presentation.mission.ui.TaskMissionScreen
import com.startup.histour.presentation.mission.util.MissionValues.INTRO_TYPE
import com.startup.histour.presentation.mission.util.MissionValues.SUBMISSION
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.onboarding.ui.CharacterScreen
import com.startup.histour.presentation.onboarding.ui.NickNameChangeScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingMapScreen
import com.startup.histour.presentation.onboarding.ui.OnBoardingScreen
import com.startup.histour.presentation.onboarding.ui.SettingScreen
import com.startup.histour.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.startup.histour.presentation.widget.snack.HistourSnackBar
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun LoginNavigationGraph(
    viewModel: OnBoardingViewModel
) {

    val navController = rememberNavController()
    val snackBarHostState = SnackbarHostState()
    Scaffold(
        snackbarHost = {
            HistourSnackBar(snackBarHostState = snackBarHostState)
        },
        containerColor = HistourTheme.colors.white000,
        contentColor = HistourTheme.colors.white000
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HistourTheme.colors.white000)
                .windowInsetsPadding( // 내비게이션 바 인셋을 처리
                    WindowInsets.systemBars.only(WindowInsetsSides.Vertical)
                ),
        ) {
            NavHost(
                navController = navController,
                startDestination = LoginScreens.Login.route
            ) {
                composable(LoginScreens.Login.route) { LoginScreen(navController) }
                composable(LoginScreens.OnBoarding.route) { OnBoardingScreen(navController) }
                composable(LoginScreens.Setting.route) { SettingScreen(navController) }
                composable(LoginScreens.Character.route) { CharacterScreen(navController) }
                composable(LoginScreens.Map.route) {
                    OnBoardingMapScreen(
                        navController,
                        snackBarHostState
                    )
                }
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HistourTheme.colors.white000)
                .windowInsetsPadding( // 내비게이션 바 인셋을 처리
                    WindowInsets.systemBars.only(WindowInsetsSides.Vertical)
                ),
        ) {
            NavHost(
                navController = navController,
                startDestination = MainScreens.BottomNavigation.route
            ) {
                composable(MainScreens.BottomNavigation.route) {
                    BottomNavigationScreen(
                        navController
                    )
                }
                composable(MainScreens.Home.route) {
                    HomeMissionScreen(navController, onNavigateToMissionMap = {
                        navController.navigate(MainScreens.MissionMap.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
                composable(MainScreens.MissionMap.route) { MissionMapScreen(navController) }
                composable(MainScreens.MissionTask.route + "/{missionId}/{initialQuizCount}",
                    arguments = listOf(navArgument("missionId") { type = NavType.IntType },
                        navArgument("initialQuizCount") { type = NavType.IntType }
                    )) { navBackStackEntry ->
                    val missionId = navBackStackEntry.arguments?.getInt("missionId") ?: 1
                    val initialQuizCount =
                        navBackStackEntry.arguments?.getInt("initialQuizCount") ?: 0
                    TaskMissionScreen(
                        navController = navController,
                        snackBarHostState = snackBarHostState,
                        submissionId = missionId,
                        initialQuizCount = initialQuizCount
                    )
                }
                composable(
                    MainScreens.MissionClear.route + "/{clearType}/{subMissionType}/{completedMissionId}/{clearedMissionCount}/{totalMissionCount}",
                    arguments = listOf(
                        navArgument("clearType") { type = NavType.StringType },
                        navArgument("subMissionType") { type = NavType.StringType },
                        navArgument("completedMissionId") { type = NavType.IntType },
                        navArgument("clearedMissionCount") { type = NavType.IntType },
                        navArgument("totalMissionCount") { type = NavType.IntType }
                    )
                )
                { navBackStackEntry ->
                    val clearType =
                        navBackStackEntry.arguments?.getString("clearType") ?: SUBMISSION
                    val subMissionType =
                        navBackStackEntry.arguments?.getString("subMissionType") ?: INTRO_TYPE
                    val completedMissionId =
                        navBackStackEntry.arguments?.getInt("completedMissionId") ?: 1
                    val clearedMissionCount =
                        navBackStackEntry.arguments?.getInt("clearedMissionCount") ?: 1
                    val totalMissionCount =
                        navBackStackEntry.arguments?.getInt("totalMissionCount") ?: 4
                    MissionClearScreen(
                        navController,
                        clearType = clearType,
                        subMissionType = subMissionType,
                        completedMissionId = completedMissionId,
                        clearedMissionCount = clearedMissionCount,
                        totalMissionCount = totalMissionCount
                    )
                }

                composable(
                    MainScreens.SubMissionChoice.route + "/{subMissionType}/{completedMissionId}",
                    arguments = listOf(
                        navArgument("subMissionType") { type = NavType.StringType },
                        navArgument("completedMissionId") { type = NavType.IntType },
                    )
                )
                { navBackStackEntry ->
                    val subMissionType =
                        navBackStackEntry.arguments?.getString("subMissionType") ?: INTRO_TYPE
                    val completedMissionId =
                        navBackStackEntry.arguments?.getInt("completedMissionId") ?: 1
                    SubMissionChoiceScreen(
                        navController,
                        subMissionType = subMissionType,
                        completedMissionId = completedMissionId
                    )
                }

                composable(MainScreens.Camera.route) { CameraScreen(navController) }
                composable(MainScreens.GPT.route + "/{taskId}",
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val taskId = navBackStackEntry.arguments?.getInt("taskId") ?: return@composable
                    GptScreen(navController, taskId)
                }
                composable(MainScreens.Bundle.route) { BundleScreen(navController) }
//                composable(MainScreens.HistoryStory.route) { HistoryStoryScreen(navController) }
                composable(
                    route = MainScreens.RecommendedSpot.route + "/{attraction}",
                    arguments = listOf(navArgument("attraction") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val attractionJson =
                        Uri.decode(navBackStackEntry.arguments?.getString("attraction"))
                    val attraction = Gson().fromJson(attractionJson, Attraction::class.java)
                    RecommendedSpotScreen(navController, attraction)
                }
                composable(MainScreens.Character.route) { CharacterScreen(navController) }
                composable(
                    route = MainScreens.CharacterSetting.route + "/{character}",
                    arguments = listOf(navArgument("character") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val characterJson =
                        Uri.decode(navBackStackEntry.arguments?.getString("character"))
                    val character = Gson().fromJson(characterJson, CharacterModel::class.java)

                    CharacterSettingScreen(navController, snackBarHostState, character)
                }
                composable(MainScreens.Setting.route) { SettingScreen(navController) }
                composable(
                    route = MainScreens.NickNameChange.route + "/{nickname}",
                    arguments = listOf(navArgument("nickname") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val nickName = navBackStackEntry.arguments?.getString("nickname").orEmpty()
                    NickNameChangeScreen(
                        navController,
                        snackBarHostState,
                        beforeNickName = nickName
                    )
                }
                composable(MainScreens.MissionClear.route) { MissionClearScreen(navController) }
                composable(MainScreens.Map.route) {
                    OnBoardingMapScreen(
                        navController,
                        snackBarHostState
                    )
                }
            }
        }
    }
}


