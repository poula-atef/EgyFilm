package com.example.egyfilm.pojo

import android.content.Context
import android.util.Log
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.retrofit.Api
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.example.egyfilm.pojo.room.MovieDao
import com.example.egyfilm.pojo.room.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object MovieRepository {
    private val apiInstance: Api = MovieRetrofitApi.getMovieRetrofitApiInstance()
    private lateinit var daoInstance: MovieDao

    suspend fun getAllGenres(context: Context): Genres? {
        return withContext(Dispatchers.IO) {
            val response = apiInstance.getAllGenres(
                Constants.API_KEY,
                Locale.getDefault().language
            )
            var result: Genres? = null
            try {
                result = response.await()
                if (result.genres.isNotEmpty())
                    addGenresToDatabase(result.genres)
                result.genres = getGenresFromDatabase(context)
            } catch (t: Throwable) {
            }
            result
        }
    }

    private suspend fun getGenresFromDatabase(context: Context): List<Genre> {
        if (!::daoInstance.isInitialized)
            daoInstance = MovieDatabase.getInstance(context).dao

        val response = daoInstance.getGenres()
        lateinit var result: List<Genre>
        try {
            result = response.await()
        } catch (t: Throwable) {
            Log.d("ViewModel", t.message!!)
        }
        return result
    }

    private fun addGenresToDatabase(genres: List<Genre>) {
        daoInstance.deleteGenres()
        for (genre in genres) {
            daoInstance.insertGenre(genre)
        }
    }
}
