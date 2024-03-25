package com.example.movies.data

import com.example.movies.network.FirebaseAuthService
import com.google.firebase.firestore.auth.User

interface AuthRepository {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
//    fun getCurrentUser(): User
}

class FirebaseAuthRepository (private val _authService: FirebaseAuthService): AuthRepository {

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.createAccount(email, password, onResult)
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.authenticate(email, password, onResult)
    }


}