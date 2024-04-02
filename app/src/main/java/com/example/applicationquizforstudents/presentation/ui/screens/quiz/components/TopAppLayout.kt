package com.example.applicationquizforstudents.presentation.ui.screens.quiz.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.ProfileDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLayout(
    modifier: Modifier = Modifier,
    route: String?,
    onSignOut: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            if (route == MenuDestination.route) Text("Меню")
            if (route == ProfileDestination.route) Text("Профиль")
        },
        actions = {
            if (route == ProfileDestination.route) ActionButton {
                onSignOut()
            }
        }
    )
}


@Composable
fun ActionButton(
    onSignOutClick: () -> Unit
) {
    IconButton(onClick = { onSignOutClick() }) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = null
        )
    }
}
