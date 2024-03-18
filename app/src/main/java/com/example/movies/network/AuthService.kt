package com.example.movies.network

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class FirebaseAuthService {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{onResult(it.exception)}
    }

    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{onResult(it.exception)}
    }
}