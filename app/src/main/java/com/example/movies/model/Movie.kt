package com.example.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie (
    val adult: Boolean,

    @SerialName(value="backdrop_path")
    val backdropPath: String? = "",
    val id: Int,
    val title: String,

    @SerialName(value="original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,

    @SerialName(value="poster_path")
    val posterPath: String? = "",

    @SerialName(value="media_type")
    val mediaType: String? = "",

    @SerialName(value="genre_ids")
    val genreIds: List<Int>,
    val popularity: Double? = 0.0,

    @SerialName(value="release_date")
    val releaseDate: String,
    val video: Boolean,

    @SerialName(value="vote_average")
    val voteAverage: Double,

    @SerialName(value="vote_count")
    val voteCount: Int

)