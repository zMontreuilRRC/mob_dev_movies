package com.example.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieData (
    val page: Int,
    var results: List<Movie>,

    @SerialName(value="total_pages")
    val totalPages: Int,

    @SerialName(value="total_results")
    val totalResults: Int
)