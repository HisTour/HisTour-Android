package com.startup.histour.presentation.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.presentation.bundle.ui.BundleScreen
import com.startup.histour.presentation.mission.ui.MissionMapScreen
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun BottomNavigationScreen(navController: NavController) {

    val bottomNavController = rememberNavController()
    val histourNavigationColor = NavigationBarItemDefaults.colors(
        selectedIconColor = HistourTheme.colors.black,
        unselectedIconColor = HistourTheme.colors.gray300,
        selectedTextColor = HistourTheme.colors.black,
        unselectedTextColor = HistourTheme.colors.gray300,
        indicatorColor = Color.Transparent
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(64.dp),
                containerColor = HistourTheme.colors.white000
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    modifier = Modifier.padding(0.dp),
                    selected = currentRoute == MainScreens.MissionMap.route,
                    onClick = {
                        bottomNavController.navigate(MainScreens.MissionMap.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = if (currentRoute == MainScreens.MissionMap.route) {
                                    painterResource(id = R.drawable.ic_mission_map_enabled)
                                } else {
                                    painterResource(id = R.drawable.ic_mission_map_disabled)
                                },
                                contentDescription = "미션 지도",
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = "미션 지도",
                                style = HistourTheme.typography.body3Medi,
                                modifier = Modifier
                                    .height(15.dp)
                                    .offset(y = -14.dp)
                                    .padding(0.dp),
                            )
                        }
                    },
                    colors = histourNavigationColor
                )
                NavigationBarItem(
                    modifier = Modifier.padding(0.dp),
                    selected = currentRoute == MainScreens.Home.route,
                    onClick = {
                        bottomNavController.navigate(MainScreens.Home.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = if (currentRoute == MainScreens.Home.route) {
                                    painterResource(id = R.drawable.ic_home_enabled)
                                } else {
                                    painterResource(id = R.drawable.ic_home_disabled)
                                },
                                contentDescription = "홈 ",
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = "홈",
                                style = HistourTheme.typography.body3Medi,
                                modifier = Modifier
                                    .height(15.dp)
                                    .offset(y = -14.dp)
                                    .padding(0.dp),
                            )
                        }
                    },
                    colors = histourNavigationColor
                )
                NavigationBarItem(
                    selected = currentRoute == MainScreens.Bundle.route,
                    onClick = {
                        bottomNavController.navigate(MainScreens.Bundle.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = if (currentRoute == MainScreens.Bundle.route) {
                                    painterResource(id = R.drawable.ic_bag_enabled)
                                } else {
                                    painterResource(id = R.drawable.ic_bag_disabled)
                                },
                                contentDescription = "보따리",
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = "보따리",
                                style = HistourTheme.typography.body3Medi,
                                modifier = Modifier
                                    .height(15.dp)
                                    .offset(y = -14.dp)
                                    .padding(0.dp),
                            )
                        }
                    },
                    colors = histourNavigationColor
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            bottomNavController,
            startDestination = MainScreens.Home.route,
            Modifier
                .background(HistourTheme.colors.white000)
                .padding(innerPadding)
        ) {
            composable(MainScreens.MissionMap.route) { MissionMapScreen(navController) }
            composable(MainScreens.Home.route) {
                HomeMissionScreen(navController, onNavigateToMissionMap = {
                    bottomNavController.navigate(MainScreens.MissionMap.route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
            composable(MainScreens.Bundle.route) { BundleScreen(navController) }
        }
    }
}