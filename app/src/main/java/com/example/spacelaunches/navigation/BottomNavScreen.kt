package com.example.spacelaunches.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val name : String,
    val id : Int ,
    val route : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
){
    object Home : BottomNavScreen(
        name = "Home",
        route= "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        id = 1

    )
    object Reminder : BottomNavScreen(
        name = "Reminders",
        route= "reminders",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        id = 2
    )

}
