package com.example.applicationquizforstudents.presentation.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.applicationquizforstudents.domain.models.ImageModel
import com.example.applicationquizforstudents.domain.models.UserRole

object Utils {

    var listResultAnswer = listOf<ImageModel>()

    var CURRENT_USER_ROLE = UserRole.USER
    fun NavHostController.navigateSingleTopTo(route: String) =
        this.navigate(route) {
            popUpTo(
                this@navigateSingleTopTo.graph.findStartDestination().id
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

}