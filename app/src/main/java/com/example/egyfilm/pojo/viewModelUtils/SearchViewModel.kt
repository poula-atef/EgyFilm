package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.classes.MovieSearch
import kotlinx.coroutines.launch

class SearchViewModel : MovieDetailsClass() {

    var searchText: String = ""
    var currentPage = 1
    private val _searchMoviesLiveData = MutableLiveData<MovieSearch>()
    val searchMoviesLiveData: LiveData<MovieSearch>
        get() = _searchMoviesLiveData


    private val _searchMoviesPagesCount = MutableLiveData<Long?>()
    val searchMoviesPagesCount: LiveData<Long?>
        get() = _searchMoviesPagesCount


    fun getSearchMovies(name: String, page: Int) {
        uiScope.launch {
            _searchMoviesLiveData.value = MovieRepository.getSearchMovies(name, page)
            searchMoviesLiveData.observeForever {
                _searchMoviesPagesCount.value = it.totalPages
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}