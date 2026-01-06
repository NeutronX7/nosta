package com.isaalutions.nosta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.isaalutions.nosta.R
import com.isaalutions.nosta.ui.custom.EmailPasswordFields
import com.isaalutions.nosta.viewmodels.RegistrationState
import com.isaalutions.nosta.viewmodels.RegistrationViewModel
import kotlinx.serialization.Serializable

@Serializable
object Registration

@Composable
fun RegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
) {

    val email = registrationViewModel.email.collectAsState().value
    val password = registrationViewModel.password.collectAsState().value
    val registrationState by registrationViewModel.registrationState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showErrors by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {

        if (registrationState is RegistrationState.Success) {
            navController.popBackStack()
        } else if (registrationState is RegistrationState.Error) {
            val errorMessage = (registrationState as RegistrationState.Error).message
                ?: "Registration failed"
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = errorMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                stringResource(R.string.welcome_nosta),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(6.dp))

            EmailPasswordFields(
                email = email,
                password = password,
                onEmailChange = { registrationViewModel.updateEmail(it) },
                onPasswordChange = { registrationViewModel.updatePassword(it) },
                showErrors = showErrors,
                isRegistration = true
            )

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showErrors = true
                    registrationViewModel.registerUser()
                },
            ) {
                Text("Register")
            }

            if (registrationState is RegistrationState.Error) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = (registrationState as RegistrationState.Error).message
                        ?: "Registration failed"
                )
            }

        }
    }

}