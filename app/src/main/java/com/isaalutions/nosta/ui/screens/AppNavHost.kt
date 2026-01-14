package com.isaalutions.nosta.ui.screens

import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.isaalutions.nosta.io.repository.AuthState
import com.isaalutions.nosta.viewmodels.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = authViewModel.authState.collectAsState()
    val startDestination = when (authState.value) {
        is AuthState.Authenticated -> Screen.Main.Home.route
        is AuthState.Unauthenticated -> Screen.Auth.Login.route
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate(Screen.Main.Home.route) {
                    popUpTo(Screen.Auth.Login.route) { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate(Screen.Auth.Login.route) {
                    popUpTo(Screen.Main.Home.route) { inclusive = true }
                }
            }
        }
    }

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: startDestination
    val showBottomBar = currentRoute.startsWith("main/")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Auth flows
            composable(Screen.Auth.Login.route) { LoginScreen(navController = navController) }
            composable(Screen.Auth.Register.route) { RegistrationScreen(navController = navController) }

            // Main flows
            composable(Screen.Main.Home.route) { HomeScreen(navController = navController) }
            composable(Screen.Main.Messages.route) { MessagingScreen(navController = navController) }
            composable(Screen.Main.Profile.route) { ProfileScreen(navController = navController) }
        }
    }
}