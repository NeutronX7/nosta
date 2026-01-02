package com.isaalutions.nosta.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.isaalutions.nosta.R
import kotlinx.serialization.Serializable

@Serializable
object Login

@Composable
fun LoginScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.welcome_nosta), style = TextStyle(fontWeight = FontWeight.Bold))
        Text(stringResource(R.string.nosta_description), style = TextStyle(fontWeight = FontWeight.Medium))

        /*TextField(
            value = "",
            onValueChange = "sd"
        )*/
    }
}