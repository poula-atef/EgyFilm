package com.example.egyfilm.pojo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.classes.Movies
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap

class MovieViewModel : ViewModel() {



    private val _genresLiveData = MutableLiveData<Genres>()
    val genresLiveData: LiveData<Genres>
        get() = _genresLiveData


    private val _genreMoviesLiveData = MutableLiveData<Movies>()
    val genreMoviesLiveData: LiveData<Movies>
        get() = _genreMoviesLiveData


    private val _genresDataCompleted = MutableLiveData<Boolean>()
    val genresDataCompleted: LiveData<Boolean>
        get() = _genresDataCompleted


    private val genresList = listOf("top_rated", "popular", "upcoming")
    val genresMap = HashMap<String, Movies>()
    private var count = 0
    private val specialGenresSize = genresList.size
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val movieApi = MovieRetrofitApi.getMovieRetrofitApiInstance()

    
    //region Get Special Genre Movies

    fun getSpecialGenreMovies(page: Int) {
        uiScope.launch {

            for (genre in genresList) {
                genresMap[genre] = getSpecialGenreMoviesSuspend(genre, page) ?: return@launch
                if (count == (specialGenresSize + _genresLiveData.value?.genres?.size!!))
                    doneGettingGenresData()
            }


        }
    }

    private suspend fun getSpecialGenreMoviesSuspend(genre: String, page: Int): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getSpecialGenreMovies(
                genre,
                Constants.API_KEY,
                Locale.getDefault().language,
                page
            )
            var result: Movies? = null
            try {
                result = response.await()
                count++

            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    private suspend fun doneGettingGenresData() {
        withContext(Dispatchers.Main) {
            _genresDataCompleted.value = true
        }
    }

    //endregion

    //region Get Genre Movies

    fun getGenreMovies(genre: Genre, page: Int) {
        uiScope.launch {
            genresMap[genre.name] = getGenreMoviesSuspend(genre.id, page) ?: return@launch
            if (count == (specialGenresSize + _genresLiveData.value?.genres?.size!!))
                doneGettingGenresData()
        }
    }

    private suspend fun getGenreMoviesSuspend(genre: Int, page: Int): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getGenreMovies(Constants.API_KEY, genre, page)
            var result: Movies? = null
            try {
                result = response.await()
                count++
            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    //endregion

    //region Get All Genres

    fun getGenres() {
        uiScope.launch {
            _genresLiveData.value = getGenresSuspend() ?: return@launch
        }
    }

    private suspend fun getGenresSuspend(): Genres? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getAllGenres(
                Constants.API_KEY,
                Locale.getDefault().language
            )
            var result: Genres? = null
            try {
                result = response.await()
            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    //endregion

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}