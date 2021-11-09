package com.example.egyfilm.pojo.viewModelUtils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.MovieFullData
import com.example.egyfilm.pojo.classes.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class GenrePageViewModel : ViewModel() {

    var currentPage: Int = 1
    var genre: Genre? = null

    private val _genreLiveData = MutableLiveData<Movies?>()
    val genresLiveData: LiveData<Movies?>
        get() = _genreLiveData

    private val _selectedMovieLiveData = MutableLiveData<MovieFullData?>()
    val selectedMovieLiveData: LiveData<MovieFullData?>
        get() = _selectedMovieLiveData

    private val genresList = listOf("top_rated", "popular", "upcoming")


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun getGenreMovies() {
        uiScope.launch {
            _genreLiveData.value = if (genresList.contains(genre?.name))
                MovieRepository.getSpecialGenreMoviesSuspend(genre!!.name, currentPage)
            else
                MovieRepository.getGenreMoviesSuspend(genre!!, currentPage)
        }
    }

    fun getMovieFullDetail(id: Long) {
        uiScope.launch {
            _selectedMovieLiveData.value = MovieRepository.getMovieFullDetailSuspend(id)
        }
    }


    fun doneSelectingMovie() {
        _selectedMovieLiveData.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}