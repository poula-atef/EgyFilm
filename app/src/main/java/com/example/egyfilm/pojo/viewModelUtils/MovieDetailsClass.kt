package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.MovieFullData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class MovieDetailsClass : CoroutineViewModel() {

    protected val _selectedMovieLiveData = MutableLiveData<MovieFullData?>()
    val selectedMovieLiveData: LiveData<MovieFullData?>
        get() = _selectedMovieLiveData


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
        doneSelectingMovie()
    }

}


