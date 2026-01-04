package com.isaalutions.nosta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.isaalutions.nosta.ui.custom.EmailPasswordFields
import com.isaalutions.nosta.viewmodels.RegistrationViewModel
import kotlinx.serialization.Serializable

@Serializable
object Registration

@Composable
fun RegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {

    val email = registrationViewModel.email.collectAsState().value
    val password = registrationViewModel.password.collectAsState().value
    var showErrors by remember { androidx.compose.runtime.mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmailPasswordFields(
            email = email,
            password = password,
            onEmailChange = { registrationViewModel.updateEmail(it) },
            onPasswordChange = { registrationViewModel.updatePassword(it) },
            showErrors = showErrors
        )

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Registration)
            },
        ) {
            Text("Register")
        }

    }

}