package com.example.movies.data

import android.util.Log
import com.example.movies.network.FirestoreService

const val MOVIELIKE_TABLE = "movieLikes"
interface MovieLikeRepository {
    fun addMovieLike(userId: String, movieId: Int, onResult: (Throwable?) -> Unit)
    fun getLikedMovies(userId: String, onResult: (Throwable?) -> Unit)
}

class FirestoreMovieLikeRepository(private val _firestoreService: FirestoreService): MovieLikeRepository {

    override fun addMovieLike(userId: String, movieId: Int, onResult: (Throwable?) -> Unit) {
        val movieLike = hashMapOf(
            userId to "userId",
            movieId to "movieId"
        )

        _firestoreService.db.collection(MOVIELIKE_TABLE)
            .add(movieLike)
            .addOnSuccessListener {
                documentReference ->
                Log.d("POST:MOVIELIKE", "Movie Like added with Id: ${documentReference.id}" )
            }
            .addOnFailureListener {
                e: Exception ->
                Log.w("POST:MOVIELIKE",
                    "Failed to add Movie ${movieId} and User ${userId}. ${e.message}"
                )
            }
    }

    override fun getLikedMovies(userId: String, onResult: (Throwable?) -> Unit) {
        _firestoreService.db.collection(MOVIELIKE_TABLE)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                documents ->
                for(document in documents) {
                    Log.d("GET:MOVIELIKE", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                e: Exception ->
                Log.w("GET:MOVIELIKE", "Failed to get documents (${e})")
            }
    }
}
