package com.example.movies.data

import com.example.movies.model.Movie
import com.example.movies.model.MovieData
import com.example.movies.network.MovieApiService

interface MovieApiRepository {
    suspend fun getMovieData(): MovieData
    suspend fun searchMovie(searchTitle: String): MovieData
}

class NetworkMovieApiRepository(
    // pass in any type of API service (such as retrofit or fake)
    private val movieApiService: MovieApiService
): MovieApiRepository {
    override suspend fun getMovieData(): MovieData {
        return movieApiService.getMovies()
    }

    override suspend fun searchMovie(searchTitle: String): MovieData {
        return movieApiService.searchMovies(searchTitle = searchTitle)
    }
}

class FakeMovieApiRepository(): MovieApiRepository {

    private val _fakeData = FakeRepoData().fakeMovieData
    override suspend fun getMovieData(): MovieData {
        return _fakeData
    }

    override suspend fun searchMovie(searchTitle: String): MovieData {
        var newData = _fakeData.copy()

        newData.results = newData.results.filter { it.title.contains(
            searchTitle,
            ignoreCase = true)
        }

        return newData
    }

}

class FakeRepoData() {
    var movie1 = Movie(
        adult = false,
        backdropPath = "/backdrop1.jpg",
        id = 1,
        title = "Aces High",
        originalLanguage = "en",
        originalTitle = "Aces High",
        overview = "Overview of Movie 1",
        posterPath = "/poster1.jpg",
        mediaType = "movie",
        genreIds = listOf(1, 2, 3),
        popularity = 7.8,
        releaseDate = "2024-01-01",
        video = false,
        voteAverage = 7.5,
        voteCount = 1000
    )

    var movie2 = Movie(
        adult = false,
        backdropPath = "/backdrop2.jpg",
        id = 2,
        title = "Jokers Wild",
        originalLanguage = "en",
        originalTitle = "Jokers Wild",
        overview = "Overview of Movie 2",
        posterPath = "/poster2.jpg",
        mediaType = "movie",
        genreIds = listOf(4, 5),
        popularity = 8.2,
        releaseDate = "2024-02-15",
        video = false,
        voteAverage = 8.0,
        voteCount = 1500
    )

    var movie3 = Movie(
        adult = false,
        backdropPath = "/backdrop3.jpg",
        id = 3,
        title = "Read 'em and Weep",
        originalLanguage = "en",
        originalTitle = "Read 'em and Weep",
        overview = "Overview of Movie 3",
        posterPath = "/poster3.jpg",
        mediaType = "movie",
        genreIds = listOf(6),
        popularity = 6.5,
        releaseDate = "2024-03-10",
        video = true,
        voteAverage = 6.8,
        voteCount = 800
    )

    var movie4 = Movie(
        adult = true,
        backdropPath = "/backdrop4.jpg",
        id = 4,
        title = "Royal Flush",
        originalLanguage = "en",
        originalTitle = "Royal Flush",
        overview = "Overview of Movie 4",
        posterPath = "/poster4.jpg",
        mediaType = "movie",
        genreIds = listOf(7, 8),
        popularity = 7.0,
        releaseDate = "2024-04-20",
        video = false,
        voteAverage = 7.2,
        voteCount = 1200
    )

    var movie5 = Movie(
        adult = false,
        backdropPath = "/backdrop5.jpg",
        id = 5,
        title = "A Few Cards Short",
        originalLanguage = "en",
        originalTitle = "A Few Cards Short",
        overview = "Overview of Movie 5",
        posterPath = "/poster5.jpg",
        mediaType = "movie",
        genreIds = listOf(9, 10),
        popularity = 7.5,
        releaseDate = "2024-05-05",
        video = true,
        voteAverage = 7.6,
        voteCount = 900
    )

    var fakeMovieData = MovieData(
        page = 1,
        results = mutableListOf(movie1, movie2, movie3, movie4, movie5),
        totalPages = 1000,
        totalResults = 1000
    )

}
