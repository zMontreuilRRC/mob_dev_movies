package com.example.movies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.model.Movie
import com.example.movies.model.MovieData
import kotlinx.coroutines.InternalCoroutinesApi


/*
To be implemented by Room
Entities specify entity classes available to DB
Version should increment per migration/schema change
ExportSchema specifies version history backups
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        // prevents caching so in-memory version is always used, up to date
        @Volatile
        private var Instance: MovieDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): MovieDatabase {
            // singleton pattern implementation
            // synchronized wrapper prevents asynchronous call, prevent multiple instances
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

