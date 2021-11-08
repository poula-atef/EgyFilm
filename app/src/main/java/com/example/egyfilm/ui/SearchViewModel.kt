package com.example.egyfilm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.MovieFullData
import com.example.egyfilm.pojo.classes.MovieSearch
import com.example.egyfilm.pojo.viewModelUtils.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var searchText: String = ""
    var currentPage = 1
    private val _searchMoviesLiveData = MutableLiveData<MovieSearch>()
    val searchMoviesLiveData: LiveData<MovieSearch>
        get() = _searchMoviesLiveData


    private val _selectedMovieLiveData = MutableLiveData<MovieFullData?>()
    val selectedMovieLiveData: LiveData<MovieFullData?>
        get() = _selectedMovieLiveData

    private val _searchMoviesPagesCount = MutableLiveData<Long?>()
    val searchMoviesPagesCount: LiveData<Long?>
        get() = _searchMoviesPagesCount


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    fun getMovieFullDetail(id: Long) {
        uiScope.launch {
            _selectedMovieLiveData.value = MovieRepository.getMovieFullDetailSuspend(id)
        }
    }


    fun getSearchMovies(name: String, page: Int) {
        uiScope.launch {
            _searchMoviesLiveData.value = MovieRepository.getSearchMovies(name, page)
            searchMoviesLiveData.observeForever {
                _searchMoviesPagesCount.value = it.totalPages
            }
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