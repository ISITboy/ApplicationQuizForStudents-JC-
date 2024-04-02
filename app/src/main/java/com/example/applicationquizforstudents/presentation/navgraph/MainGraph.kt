package com.example.applicationquizforstudents.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.applicationquizforstudents.presentation.ui.Utils.navigateSingleTopTo
import com.example.applicationquizforstudents.presentation.ui.screens.QuizNavigator
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin.AuthorizationScreen
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.RegistrationScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreenViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.result.ResultScreen

@Composable
fun NavGraph(
    startDestination: String,
    onSignIn:()->Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = AuthorizationNavigation.route,
            startDestination = AuthorizationDestination.route
        ) {
            composable(route = AuthorizationDestination.route) {
                AuthorizationScreen(
                    onSignInClick = {
                        onSignIn()
                    },
                    onGoToRegistrationScreen = {authData->
                        val result = authData.ifBlank { "empty" }
                        navController.navigateToRegistration(result)
                    }
                )
            }
            composable(
                route = RegistrationDestination.routeWithArgs,
                arguments = RegistrationDestination.arguments
            ){navBackStackEntry ->
                val registrationType =
                    navBackStackEntry.arguments?.getString(RegistrationDestination.authData)
                RegistrationScreen(authData = registrationType)
            }
        }
        navigation(
            route = QuizNavigation.route,
            startDestination = QuizNavigationScreen.route
        ) {
            composable(route = QuizNavigationScreen.route){
                QuizNavigator(_navController = navController)
            }
            composable(route = QuestionDestination.route) {
                val viewModel: QuestionScreenViewModel = hiltViewModel()
                QuestionScreen(){
                    navController.navigateSingleTopTo(ResultDestination.route)
                }
            }
            composable(route = ResultDestination.route) {
                val viewModel: QuestionScreenViewModel = hiltViewModel()
                ResultScreen(){

                }
            }
        }
    }
}


private fun NavHostController.navigateToRegistration(accountType: String) {
    this.navigateSingleTopTo("${RegistrationDestination.route}/$accountType")
}