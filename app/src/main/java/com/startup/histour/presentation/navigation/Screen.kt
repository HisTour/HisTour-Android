package com.startup.histour.presentation.navigation

sealed class LoginScreens(val route: String) {
    data object Login : LoginScreens("login")
    data object OnBoarding : LoginScreens("onboarding")
    data object Map : MainScreens("map")
    data object Character : MainScreens("character")
    data object Setting : MainScreens("setting")
}

sealed class MainScreens(val route: String) {
    data object BottomNavigation : MainScreens("bottomNavigation")
    data object Home : MainScreens("home")
    data object MissionTask : MainScreens("taskMission")
    data object MissionMap : MainScreens("missionMap")
    data object SubMissionChoice : MainScreens("submissionChoice")
    data object MissionClear:MainScreens("clear")
    data object GPT : MainScreens("gpt")
    data object Camera : MainScreens("camera")
    data object Bundle : MainScreens("bundle")
    data object HistoryStory : MainScreens("historyStory")
    data object RecommendedSpot : MainScreens("recommendedSpot")
    data object Character : MainScreens("character")
    data object CharacterSetting : MainScreens("characterSetting")
    data object Setting : MainScreens("setting")
    data object NickNameChange : MainScreens("nickNameChange")
}