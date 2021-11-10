package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Movies
import kotlinx.coroutines.launch

class GenrePageViewModel : MovieDetailsClass() {

    var currentPage: Int = 1
    var genre: Genre? = null

    private val _genreLiveData = MutableLiveData<Movies?>()
    val genresLiveData: LiveData<Movies?>
        get() = _genreLiveData


    private val genresList = listOf("top_rated", "popular", "upcoming")


    fun getGenreMovies() {
        uiScope.launch {
            _genreLiveData.value = if (genresList.contains(genre?.name))
                MovieRepository.getSpecialGenreMoviesSuspend(genre!!.name, currentPage)
            else
                MovieRepository.getGenreMoviesSuspend(genre!!, currentPage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}