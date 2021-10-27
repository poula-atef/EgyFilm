package com.example.egyfilm.pojo.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        private lateinit var INSTANCE: MovieDatabase
        fun getInstance(context : Context): MovieDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                        context,
                    MovieDatabase::class.java,
                    "movie_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }
    }
}