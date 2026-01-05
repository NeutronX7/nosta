package com.isaalutions.nosta.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

private val EMAIL_REGEX =
    Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

fun validateEmail(email: String): String? =
    when {
        email.isBlank() -> "Email is required"
        !EMAIL_REGEX.matches(email) -> "Invalid email format"
        else -> null
    }

fun validatePassword(password: String): String? =
    when {
        password.isBlank() -> "Password is required"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailPasswordFields(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    showErrors: Boolean = false,
    isRegistration: Boolean
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val emailError = remember(email) { validateEmail(email) }
    val passwordError = remember(password) { validatePassword(password) }

    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            isError = showErrors && emailError != null,
            supportingText = {
                if (showErrors && emailError != null) {
                    Text(emailError)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            singleLine = true,
            isError = showErrors && passwordError != null,
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            supportingText = {
                if (showErrors && passwordError != null) {
                    Text(passwordError)
                }
            },
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
    }
}