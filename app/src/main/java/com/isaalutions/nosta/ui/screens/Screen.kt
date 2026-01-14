package com.isaalutions.nosta.ui.screens

sealed class Screen(val route: String) {
    object Auth {
        object Login : Screen("auth/login")
        object Register : Screen("auth/register")
    }
    object Main {
        object Home : Screen("main/home")
        object Messages : Screen("main/messages")
        object Profile : Screen("main/profile")
    }
}