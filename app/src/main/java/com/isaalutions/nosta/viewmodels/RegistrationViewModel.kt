package com.isaalutions.nosta.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    data class Success(val uid: String) : RegistrationState()
    data class Error(val message: String?) : RegistrationState()
}

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun registerUser() {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value

        if (emailValue.isEmpty() || passwordValue.length < 6) {
            _registrationState.value = RegistrationState.Error("Please provide a valid email and a password with at least 6 characters.")
            return
        }

        _registrationState.value = RegistrationState.Loading

        auth.createUserWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    _registrationState.value = RegistrationState.Success(uid)
                } else {
                    _registrationState.value = RegistrationState.Error(task.exception?.localizedMessage)
                }
            }
    }

}