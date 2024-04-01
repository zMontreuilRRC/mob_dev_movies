package com.example.movies.data

import android.util.Log
import com.example.movies.model.MovieUser
import com.example.movies.network.FirebaseAuthService

interface AuthRepository {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun getCurrentUser(): MovieUser
    fun logoutUser()
}

class FirebaseAuthRepository (private val _authService: FirebaseAuthService): AuthRepository {

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.createAccount(email, password, onResult)
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.authenticate(email, password, onResult)
    }
    override fun getCurrentUser(): MovieUser {
        val firebaseUser = _authService.getCurrentUser()
        if(firebaseUser == null) {
            throw Exception("User credentials not found")
        } else {
            return MovieUser(
                email = firebaseUser.email ?: "",
                id = firebaseUser.uid,
                isLoggedIn = firebaseUser.email != null && firebaseUser.uid != null
            )
        }
    }

    override fun logoutUser() {
        _authService.logout()
    }
}