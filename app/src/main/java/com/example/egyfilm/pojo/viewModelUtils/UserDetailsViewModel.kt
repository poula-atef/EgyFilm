package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.classes.ActorMovies
import kotlinx.coroutines.launch

class UserDetailsViewModel : MovieDetailsClass() {
    private val _actorMoviesLiveData = MutableLiveData<ActorMovies>()
    val actorMoviesLiveData: LiveData<ActorMovies>
        get() = _actorMoviesLiveData


    fun getActorMovies(id: Long) {
        uiScope.launch {
            _actorMoviesLiveData.value = MovieRepository.getActorMoviesSuspend(id)
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}