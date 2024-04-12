package com.example.movies.data

import android.util.Log
import com.example.movies.model.Movie
import com.example.movies.model.MovieLike
import com.example.movies.network.FirestoreService
import com.google.firebase.firestore.toObject

const val MOVIELIKE_TABLE = "movieLikes"
interface MovieLikeRepository {
    fun removeMovieLike(userId: String, movieId: String, onResult: (Throwable?) -> Unit)
    fun addMovieLike(userId: String, movieId: String, onResult: (Throwable?) -> Unit)
    fun getMovieLikes(userId: String, onResult: (List<MovieLike>) -> Unit)
    fun getMovieLikeId(userId: String, movieId: String, onResult: (String) -> Unit)
}

class FirestoreMovieLikeRepository(private val _firestoreService: FirestoreService): MovieLikeRepository {
    override fun addMovieLike(userId: String, movieId: String, onResult: (Throwable?) -> Unit) {
        val movieLike = hashMapOf(
            "userId" to userId,
            "movieId" to movieId
        )

        _firestoreService.db.collection(MOVIELIKE_TABLE)
            .add(movieLike)
            .addOnSuccessListener {
                documentReference ->
                Log.d("POST:MOVIELIKE", "Movie Like added with Id: ${documentReference.id}" )
                onResult(null)
            }
            .addOnFailureListener {
                e: Exception ->
                Log.w("POST:MOVIELIKE",
                    "Failed to add Movie ${movieId} and User ${userId}. ${e.message}"
                )
            }
    }

    override fun getMovieLikeId(userId: String, movieId: String, onResult: (String) -> Unit) {
        var docId: MutableList<String> = mutableListOf()

        val movieLikeQuery = _firestoreService.db.collection(MOVIELIKE_TABLE)
            .whereEqualTo("userId", userId)
            .whereEqualTo("movieId", movieId)

        movieLikeQuery.get()
            .addOnSuccessListener {
                documents ->
                for(document in documents) {
                    Log.d("MOVIELIKES:GET", document.id)
                    onResult(document.id)
                }
            }
            .addOnFailureListener {
                Log.w("MOVIELIKES:GET", "Failed to get document")
            }
    }
    override fun removeMovieLike(userId: String, movieId: String, onResult: (Throwable?) -> Unit) {
        getMovieLikeId(userId, movieId) {
            docId ->
            _firestoreService.db.collection(MOVIELIKE_TABLE)
                .document(docId)
                .delete()
                .addOnSuccessListener {
                    onResult(null)
                }
        }
    }

    override fun getMovieLikes(userId: String, onResult: (List<MovieLike>) -> Unit) {
        _firestoreService.db.collection(MOVIELIKE_TABLE)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                documents ->

                val movieLikes: MutableList<MovieLike> = mutableListOf()
                for(document in documents) {
                    val likeObject:MovieLike = document.toObject<MovieLike>()
                    movieLikes.add(likeObject)
                }

                onResult(movieLikes)
            }
            .addOnFailureListener {
                e: Exception ->
                Log.w("GET:MOVIELIKE", "Failed to get documents (${e})")
            }
    }
}
