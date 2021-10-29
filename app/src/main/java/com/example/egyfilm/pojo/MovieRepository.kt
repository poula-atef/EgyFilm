package com.example.egyfilm.pojo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.classes.Movies
import com.example.egyfilm.pojo.retrofit.Api
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.example.egyfilm.pojo.room.MovieDao
import com.example.egyfilm.pojo.room.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import android.net.NetworkInfo


import android.net.ConnectivityManager
import com.bumptech.glide.Glide


object MovieRepository {
    private val movieApi: Api = MovieRetrofitApi.getMovieRetrofitApiInstance()
    private lateinit var daoInstance: MovieDao
    private var count = 0
    private val _genresDataCompleted = MutableLiveData<HashMap<String, Movies>>()
    val genresDataCompleted: LiveData<HashMap<String, Movies>>
        get() = _genresDataCompleted
    private val genresMap = HashMap<String, Movies>()
    private val genresList = listOf("top_rated", "popular", "upcoming")
    private val specialGenresSize = genresList.size


    //region Normal Genre Part

    suspend fun getAllGenres(context: Context): Genres? {
        if (!::daoInstance.isInitialized)
            daoInstance = MovieDatabase.getInstance(context).dao

        return withContext(Dispatchers.IO) {
            val response = movieApi.getAllGenres(
                Constants.API_KEY,
                Locale.getDefault().language
            )
            var result: Genres? = null
            try {
                if (MovieViewModel.isConnected) {
                    result = response.await()
                    if (result.genres?.isNotEmpty() == true)
                        addGenresToDatabase(result.genres!!, context)
                }
                else
                    result = Genres()
                result.genres = getGenresFromDatabase()
                Log.d("getGenreMoviesSuspend", "genres are here ${result.genres}")

            } catch (t: Throwable) {
            }
            result
        }
    }

    private suspend fun getGenresFromDatabase(): List<Genre> {
        return daoInstance.getGenres()
    }

    private fun addGenresToDatabase(genres: List<Genre>, context: Context) {

        daoInstance.deleteGenres()
        for (genre in genres) {
            daoInstance.insertGenre(genre)
        }
    }

    //endregion


    suspend fun getGenreMovies(genre: Genre, page: Int, genresCount: Int, context: Context) {
        genresMap[genre.name] = getGenreMoviesSuspend(genre, page, context) ?: return
        Log.d(
            "getGenreMoviesSuspend",
            "count is $count and now is ${(specialGenresSize + genresCount)}"
        )
        if (count == (specialGenresSize + genresCount))
            doneGettingGenresData()

    }

    private suspend fun getGenreMoviesSuspend(genre: Genre, page: Int, context: Context): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getGenreMovies(Constants.API_KEY, genre.id, page)
            var result: Movies? = null
            try {
                if (MovieViewModel.isConnected) {
                    result = response.await()
                    if (result.results?.isNotEmpty() == true) {
                        addGenreMoviesToDatabase(result, genre.name)
                    }
                }
                else
                    result = Movies()
                result.results = getGenreMoviesFromDatabase(genre.name)
                count++
                Log.d("getSpecialGenreMoviesSuspend", "from normal $count")

            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    private fun addGenreMoviesToDatabase(result: Movies, genre: String) {
        result.results?.forEach { movie ->
            movie.category = genre
            daoInstance.insertGenreMovie(movie)
        }
    }

    private fun getGenreMoviesFromDatabase(genre: String): List<Movie> {
        return daoInstance.getGenreMovies(genre)
    }


    private suspend fun doneGettingGenresData() {
        withContext(Dispatchers.Main) {
            _genresDataCompleted.value = genresMap
            Log.d("getGenreMoviesSuspend", "data is ready ${genresMap.toString()}")
        }
    }


    suspend fun getSpecialGenreMovies(page: Int, genresCount: Int, context: Context) {
        for (genre in genresList) {
            genresMap[genre] = getSpecialGenreMoviesSuspend(genre, page, context) ?: return
            Log.d(
                "getGenreMoviesSuspend",
                "count is $count and now is ${(specialGenresSize + genresCount)}"
            )
            if (count == (specialGenresSize + genresCount))
                doneGettingGenresData()
        }

    }

    suspend fun getSpecialGenreMoviesSuspend(
        genre: String,
        page: Int,
        context: Context
    ): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getSpecialGenreMovies(
                genre,
                Constants.API_KEY,
                Locale.getDefault().language,
                page
            )
            var result: Movies? = null
            try {
                if (MovieViewModel.isConnected) {
                    result = response.await()
                    if (result.results?.isNotEmpty() == true)
                        addGenreMoviesToDatabase(result, genre)
                }
                else
                    result = Movies()
                result?.results = getGenreMoviesFromDatabase(genre)
                count++
                Log.d("getSpecialGenreMoviesSuspend", "from special $count")
            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }


    /*            movie.posterImage = Glide.with(context).`as`(ByteArray::class.java).load(movie.posterPath).submit().get()
            movie.backdropImage = Glide.with(context).`as`(ByteArray::class.java).load(movie.posterPath).submit().get()
            BitmapDrawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length))
            */


}
