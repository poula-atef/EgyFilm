package com.example.egyfilm.pojo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.egyfilm.pojo.classes.*

@Database(
    entities = [Movie::class, ActorMovie::class,
        ActorFullData::class, Actor::class, Genre::class,
        MovieFullData::class, MovieActors::class,ActorMovies::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        private lateinit var INSTANCE: MovieDatabase
        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MovieDatabase::class.java,
                        "movie_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }
    }
}