package com.example.applicationquizforstudents.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.ProfileDestination
import com.example.applicationquizforstudents.presentation.navgraph.QuestionDestination
import com.example.applicationquizforstudents.presentation.navgraph.tabRowScreens
import com.example.applicationquizforstudents.presentation.ui.Utils.navigateSingleTopTo
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.components.BottomNavBar
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.components.TopAppBarLayout
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuEvent
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu.MenuScreenViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile.ProfileScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile.ProfileScreenViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreenViewModel

@Composable
fun QuizNavigator(modifier: Modifier = Modifier,_navController: NavHostController) {
    val currentScreen = remember { tabRowScreens }
    val navController = rememberNavController()
    val backStackState by navController.currentBackStackEntryAsState()
    val rote = backStackState?.destination?.route
    Scaffold(
        topBar = {
            TopAppBarLayout(route = rote) {

            }
        },
        bottomBar = {
            BottomNavBar(
                allScreens = currentScreen,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = getCurrentScreen(backStackState?.destination)
            )
        }
    ) { paddingValues ->
        AppNavHost(
            paddingValues = paddingValues,
            navController = navController,
            _navController = _navController
        )
    }
}

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    _navController: NavHostController
) {

    NavHost(
        navController = navController,
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
                        _navController.navigate(route = QuestionDestination.route)
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