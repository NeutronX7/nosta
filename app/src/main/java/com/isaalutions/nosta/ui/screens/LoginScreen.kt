package com.isaalutions.nosta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.isaalutions.nosta.R
import com.isaalutions.nosta.viewmodels.LoginViewModel
import kotlinx.serialization.Serializable

@Serializable
object Login


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var email = viewModel.email.collectAsState().value
    var password = viewModel.password.collectAsState().value
    var passwordVisible = false

    // Google login launcher (Credential Manager)
    /*val googleSignIn = rememberGoogleSignIn(
        onIdToken = { idToken ->
            // Send token to backend or Firebase, etc.
            viewModel.loginWithGoogleIdToken(idToken)
        },
        onError = { error ->
            viewModel.onUiError(error)
        }
    )*/

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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.loginWithEmail(email.trim(), password)
                // Example navigation after success (you’ll probably do this via state)
                // navController.navigate("home") { popUpTo("login") { inclusive = true } }
            },
            //enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.loginWithEmail(email.trim(), password)
                // Example navigation after success (you’ll probably do this via state)
                // navController.navigate("home") { popUpTo("login") { inclusive = true } }
            },
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Register")
        }

        Spacer(Modifier.height(12.dp))

        HorizontalDivider()

        // Google sign in
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