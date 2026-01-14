package com.isaalutions.nosta.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

private data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Screen.Main.Home.route, Icons.Default.Home),
        BottomNavItem("Messages", Screen.Main.Messages.route, Icons.AutoMirrored.Outlined.Message),
        BottomNavItem("Profile", Screen.Main.Profile.route, Icons.Default.Person)
    )

    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex.intValue == index,
                onClick = {
                    selectedIndex.intValue = index
                    navController.navigate(item.route) {
                        popUpTo(Screen.Main.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}