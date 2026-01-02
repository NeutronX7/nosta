package com.isaalutions.nosta.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object Login

@Composable
fun LoginScreen(
    navController: NavController
) {
    Text("Este es el login")
}