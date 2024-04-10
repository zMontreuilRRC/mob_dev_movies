package com.example.movies.network

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class FirebaseAuthService {
    private var auth: FirebaseAuth = Firebase.auth

    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful) {
                    onResult(null)
                } else {
                    onResult(Exception(task.exception?.message))
                }
            }
    }

    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful) {
                    onResult(null)
                } else {
                    onResult(Exception(task.exception?.message))
            }
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}