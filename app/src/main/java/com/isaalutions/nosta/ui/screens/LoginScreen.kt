package com.isaalutions.nosta.ui.screens

import Home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.isaalutions.nosta.viewmodels.LoginState
import com.isaalutions.nosta.viewmodels.LoginViewModel
import kotlinx.serialization.Serializable

@Serializable
object Login

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val email = viewModel.email.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val loginState by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showErrors by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        if (loginState is LoginState.Success) {
            navController.navigate(Home)
        } else if (loginState is LoginState.Error) {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = (loginState as LoginState.Error).message.toString(),
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
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
            Text(
                stringResource(R.string.nosta_description),
                style = TextStyle(fontWeight = FontWeight.Medium)
            )

            Spacer(Modifier.height(24.dp))

            EmailPasswordFields(
                email = email,
                password = password,
                onEmailChange = { viewModel.updateEmail(it) },
                onPasswordChange = { viewModel.updatePassword(it) },
                showErrors = showErrors,
                isRegistration = false
            )

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.loginWithEmail()
                },
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Registration)
                },
            ) {
                Text("Register")
            }

            Spacer(Modifier.height(12.dp))

            HorizontalDivider()

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    //googleSignIn.start()
                }
            ) {
                Text("Continue with Google")
            }
        }
    }
}