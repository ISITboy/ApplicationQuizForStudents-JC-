package com.example.applicationquizforstudents.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.NavGraphState
import com.example.applicationquizforstudents.presentation.navgraph.ProfileDestination
import com.example.applicationquizforstudents.presentation.navgraph.QuestionDestination
import com.example.applicationquizforstudents.presentation.navgraph.rememberAppState
import com.example.applicationquizforstudents.presentation.navgraph.tabRowScreens
import com.example.applicationquizforstudents.presentation.ui.Utils.navigateSingleTopTo
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.components.BottomNavBar
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.components.TopAppBarLayout
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuEvent
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuScreenViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile.ProfileScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile.ProfileScreenViewModel

@Composable
fun QuizNavigator(modifier: Modifier = Modifier, mainNavController: NavHostController) {
    val currentScreen = remember { tabRowScreens }
    val navGraphState = rememberAppState()
    val backStackState by navGraphState.navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                allScreens = currentScreen,
                onTabSelected = { newScreen ->
                    navGraphState.navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = getCurrentScreen(backStackState?.destination)
            )
        }
    ) { paddingValues ->
        AppNavHost(
            paddingValues = paddingValues,
            appState = navGraphState,
            mainNavController = mainNavController
        )
    }
}

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    appState: NavGraphState,
    mainNavController: NavHostController
) {

    NavHost(
        navController = appState.navController,
        startDestination = MenuDestination.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = MenuDestination.route) {
            val viewModel: MenuScreenViewModel = hiltViewModel()
            MenuScreen(onEvent = {
                when(it){
                    MenuEvent.ToShowInfo ->{

                    }
                    MenuEvent.ToStartTest ->{
                        mainNavController.navigate(route = QuestionDestination.route)
                    }
                }

            })
        }
        composable(route = ProfileDestination.route) {
            val viewModel: ProfileScreenViewModel = hiltViewModel()
            ProfileScreen(onEvent = viewModel::onEvent)
        }
    }
}

private fun getCurrentScreen(currentDestination: NavDestination?) =
    tabRowScreens.find { it.route == currentDestination?.route } ?: MenuDestination