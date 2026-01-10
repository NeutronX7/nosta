package com.isaalutions.nosta.io.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class AuthState {
    data object Unauthenticated : AuthState()
    data class Authenticated(val uid: String) : AuthState()
}

class AuthRepository (
    private val auth: FirebaseAuth
) {
    fun authState(): Flow<AuthState> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { fa ->
            val user = fa.currentUser
            trySend(
                if (user != null) AuthState.Authenticated(user.uid)
                else AuthState.Unauthenticated
            )
        }

        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    fun signOut() {
        auth.signOut()
    }
}