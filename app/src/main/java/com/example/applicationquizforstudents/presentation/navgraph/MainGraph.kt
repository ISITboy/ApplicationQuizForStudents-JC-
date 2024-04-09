package com.example.applicationquizforstudents.presentation.navgraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.applicationquizforstudents.presentation.ui.Utils.navigateSingleTopTo
import com.example.applicationquizforstudents.presentation.ui.screens.QuizNavigator
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin.AuthorizationScreen
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.RegistrationScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.editimage.EditImageScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.editimage.EditImageViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreenViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.result.ResultScreen
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.result.ResultViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.splash.SplashScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(startDestination: MutableState<String>) {

    val navGraphState = rememberAppState()

    NavHost(
        navController = navGraphState.navController,
        startDestination = startDestination.value
    ) {
        authorizationGraph(appState = navGraphState, startDestination)
        quizGraph(appState = navGraphState)
        navigation(
            route = "SplashNavigation",
            startDestination = SplashDestination.route
        ) {
            composable(route = SplashDestination.route) {
                SplashScreen(openAndPopUp = { route, _ ->
                    if (route == AuthorizationDestination.route) startDestination.value =
                        AuthorizationNavigation.route
                    if (route == MenuDestination.route) startDestination.value =
                        QuizNavigation.route
                })
            }
        }
    }
}

fun NavGraphBuilder.authorizationGraph(
    appState: NavGraphState,
    startDestination: MutableState<String>
) {
    navigation(
        route = AuthorizationNavigation.route,
        startDestination = AuthorizationDestination.route
    ) {
        composable(route = AuthorizationDestination.route) {
            AuthorizationScreen(
                openAndPopUp = { route, _ ->
                    if (route == MenuDestination.route) startDestination.value =
                        QuizNavigation.route
                    else appState.navigate(route)
                }
            )
        }
        composable(
            route = RegistrationDestination.routeWithArgs,
            arguments = RegistrationDestination.arguments
        ) { navBackStackEntry ->
            val registrationType =
                navBackStackEntry.arguments?.getString(RegistrationDestination.authData)
            RegistrationScreen(authData = registrationType) {
                appState.popUp()
            }
        }
    }
}

fun NavGraphBuilder.quizGraph(appState: NavGraphState) {
    navigation(
        route = QuizNavigation.route,
        startDestination = QuizNavigationScreen.route
    ) {
        composable(route = QuizNavigationScreen.route) {
            QuizNavigator(mainNavController = appState.navController)
        }
        composable(route = QuestionDestination.route) {
            val viewModel: QuestionScreenViewModel = hiltViewModel()
            QuestionScreen(
                onFinisClick = {
                    appState.navigateAndPopUp(ResultDestination.route, QuestionDestination.route)
                },
                onEditImage = {
                    //val encodedUri = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                    //appState.navigate("${EditImageDestination.route}/$encodedUri")
                    appState.navigate("${EditImageDestination.route}/$it")
                }
            )
        }
        composable(route = ResultDestination.route) {
            val viewModel: ResultViewModel = hiltViewModel()
            ResultScreen(viewModel = viewModel) {
                appState.popUp()
            }
        }
        composable(
            route = EditImageDestination.routeWithArgs,
            arguments = EditImageDestination.arguments
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getInt(EditImageDestination.editTypeArg)
            val viewModel:EditImageViewModel = hiltViewModel()
            EditImageScreen(editType = accountType, viewModel = viewModel, onDeleteClick = { appState.popUp() },
                onBackClick = {
                    appState.popUp()
                },
                onCreateImageClick = {photoUrl, desc->
                    viewModel.createImage(photoUrl,desc)
                    appState.popUp()
                },
                onUpdateImageClick = {image, id->
                    viewModel.updateImage(image,id)
                    appState.popUp()
                }
            )
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        NavGraphState(navController)
    }


private fun NavHostController.navigateToRegistration(accountType: String) {
    this.navigateSingleTopTo("${RegistrationDestination.route}/$accountType")
}