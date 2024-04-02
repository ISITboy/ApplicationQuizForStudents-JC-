package com.example.applicationquizforstudents.presentation.ui.screens.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.presentation.navgraph.BottomNavigationPoint
import com.example.applicationquizforstudents.presentation.navgraph.Destination

@Composable
fun BottomNavBar(
    allScreens: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentScreen: Destination,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        allScreens.forEach{ screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = { TabItem(point = screen.point)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.selected_tab_icon),
                    selectedTextColor = colorResource(id = R.color.selected_tab_icon),
                    unselectedIconColor = colorResource(id = R.color.unselected_tab_icon),
                    unselectedTextColor = colorResource(id = R.color.unselected_tab_icon),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Composable
fun TabItem(point: BottomNavigationPoint?){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Icon(
            imageVector = point!!.icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = stringResource(id = point.label), style = MaterialTheme.typography.labelSmall)
    }
}