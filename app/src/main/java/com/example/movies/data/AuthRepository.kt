package com.example.movies.data

import android.util.Log
import com.example.movies.model.MovieUser
import com.example.movies.network.FirebaseAuthService

interface AuthRepository {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun getCurrentUser(): MovieUser
    fun logoutUser()
    val movieUser: MovieUser
}

class FirebaseAuthRepository (private val _authService: FirebaseAuthService): AuthRepository {

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.createAccount(email, password, onResult)
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.authenticate(email, password, onResult)
    }
    override fun getCurrentUser(): MovieUser {
        if(!movieUser.isLoggedIn) {
            val firebaseUser = _authService.getCurrentUser()
            if(firebaseUser == null) {
                throw Exception("User credentials not found")
            } else {
                movieUser = movieUser.copy(
                    email = firebaseUser ?.email ?: "",
                    id = firebaseUser.uid,
                    isLoggedIn = true
                )
            }
        }
        return movieUser
    }

    override var movieUser: MovieUser = MovieUser()

    override fun logoutUser() {
        _authService.logout()
    }
}