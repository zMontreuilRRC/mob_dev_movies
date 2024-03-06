package com.example.movies.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "movies")
@Serializable
data class Movie (
    var adult: Boolean?,

    @SerialName(value="backdrop_path")
    var backdropPath: String? = "",

    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var title: String,

    @SerialName(value="original_language")
    var originalLanguage: String?,

    @SerialName("original_title")
    var originalTitle: String?,
    var overview: String?,

    @SerialName(value="poster_path")
    var posterPath: String? = "",

    @SerialName(value="media_type")
    var mediaType: String? = "",

    // cannot store collections in SQL
//    @Ignore
//    @SerialName(value="genre_ids")
//    var genreIds: List<Int>,
    var popularity: Double? = 0.0,

    @SerialName(value="release_date")
    var releaseDate: String?,
    var video: Boolean?,

    @SerialName(value="vote_average")
    var voteAverage: Double?,

    @SerialName(value="vote_count")
    var voteCount: Int?
)
