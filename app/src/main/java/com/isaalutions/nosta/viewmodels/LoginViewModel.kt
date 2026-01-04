package com.isaalutions.nosta.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun loginWithEmail(email: String, password: String) {
        // TODO: call your repo / backend
    }

    fun loginWithGoogleIdToken(idToken: String) {
        // TODO: send token to backend or exchange it for a session
    }

    fun onUiError(message: String) {
        // TODO: expose snackbar / toast state
    }
}