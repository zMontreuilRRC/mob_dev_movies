package com.example.movies.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    /* Since we can only insert data through this DAO, we do not expect conflicts
    to occur during the insertion.
    The conflicting statement will be ignored
    These methods are convenience methods supplied by room
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    /* flow is automatically asynchronous
    Room does not have a convenience method for this so we add it as a manual query
     */
    @Query("SELECT * FROM movies ORDER BY title DESC")
    suspend fun getAllMovies(): List<Movie>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}