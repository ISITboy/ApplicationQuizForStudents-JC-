package com.example.applicationquizforstudents.presentation.navgraph

import android.graphics.drawable.VectorDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.applicationquizforstudents.R

interface Destination {
    val route: String
    val point: BottomNavigationPoint?
}

object AuthorizationNavigation :Destination{
    override val route: String
        get() = "AuthorizationNavigation"
    override val point: BottomNavigationPoint?
        get() = null
}
object AuthorizationDestination :Destination{
    override val route: String
        get() = "AuthorizationDestination"
    override val point: BottomNavigationPoint?
        get() = null
}

object RegistrationDestination :Destination{
    const val authData = "authData"
    val routeWithArgs = "${route}/{${authData}}"
    val arguments = listOf(
        navArgument(authData) { type = NavType.StringType }
    )
    override val route: String
        get() = "RegistrationDestination"
    override val point: BottomNavigationPoint?
        get() = null
}


object QuizNavigation :Destination{
    override val route: String
        get() = "QuizNavigation"
    override val point: BottomNavigationPoint?
        get() = null
}
object QuizNavigationScreen :Destination{
    override val route: String
        get() = "QuizNavigationScreen"
    override val point: BottomNavigationPoint?
        get() = null
}

object ProfileDestination :Destination{
    override val route: String
        get() = "ProfileDestination"
    override val point: BottomNavigationPoint?
        get() = BottomNavigationPoint(
            icon = Icons.Default.AccountCircle,
            label = R.string.navigation_item_profile
        )
}

object MenuDestination :Destination{
    override val route: String
        get() = "MenuDestination"
    override val point: BottomNavigationPoint?
        get() = BottomNavigationPoint(
            icon = Icons.Default.Menu,
            label = R.string.navigation_item_menu
        )
}

object QuestionDestination :Destination{
    override val route: String
        get() = "QuestionDestination"
    override val point: BottomNavigationPoint?
        get() = null
}

object ResultDestination :Destination{
    override val route: String
        get() = "ResultDestination"
    override val point: BottomNavigationPoint?
        get() = null
}

object SplashDestination :Destination{
    override val route: String
        get() = "SplashDestination"
    override val point: BottomNavigationPoint?
        get() = null
}

object EditImageDestination :Destination{
    override val route: String
        get() = "EditImageDestination"

    const val editTypeArg = "edit_type"
    val routeWithArgs = "${route}/{${editTypeArg}}"
    val arguments = listOf(
        navArgument(editTypeArg) { type = NavType.IntType }
    )
    override val point: BottomNavigationPoint?
        get() = null
}






val tabRowScreens = listOf(
    MenuDestination,
    ProfileDestination
)


data class BottomNavigationPoint(
    val icon: ImageVector,
    @StringRes val label: Int
)