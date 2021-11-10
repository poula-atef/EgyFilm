package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.classes.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel : MovieDetailsClass() {

    private val _movieActorsLiveData = MutableLiveData<MovieActors>()
    val movieActorsLiveData: LiveData<MovieActors>
        get() = _movieActorsLiveData



    private val _movieTrailerLiveData = MutableLiveData<MovieTrailers>()
    val movieTrailerLiveData: LiveData<MovieTrailers>
        get() = _movieTrailerLiveData


    private val _movieRecommendationsLiveData = MutableLiveData<MovieRelative>()
    val movieRecommendationsLiveData: LiveData<MovieRelative>
        get() = _movieRecommendationsLiveData

    private val _movieSimilarsLiveData = MutableLiveData<MovieRelative>()
    val movieSimilarsLiveData: LiveData<MovieRelative>
        get() = _movieSimilarsLiveData


    fun getMovieActors(id: Long) {
        uiScope.launch {
            _movieActorsLiveData.value = MovieRepository.getMovieActorsSuspend(id)
        }
    }

    fun getMovieRelatives(id: Long, page: Int, relationship: String) {
        uiScope.launch {
            if (relationship.equals(Constants.RECOMMENDATIONS))
                _movieRecommendationsLiveData.value =
                    MovieRepository.getMovieRelativesSuspend(id, page, relationship)
            else
                _movieSimilarsLiveData.value =
                    MovieRepository.getMovieRelativesSuspend(id, page, relationship)
        }
    }

    fun getMovieTrailer(movieId: Long) {
        uiScope.launch {
            _movieTrailerLiveData.value = MovieRepository.getMovieTrailers(movieId)
        }

    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}