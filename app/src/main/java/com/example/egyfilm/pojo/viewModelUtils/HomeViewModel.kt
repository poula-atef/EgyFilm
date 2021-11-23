package com.example.egyfilm.pojo.viewModelUtils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.classes.Movies
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context) : MovieDetailsClass() {

    private val _genresLiveData = MutableLiveData<Genres>()
    val genresLiveData: LiveData<Genres>
        get() = _genresLiveData

    private val _genresDataCompleted = MutableLiveData<HashMap<String, Movies>>()
    val genresDataCompleted: LiveData<HashMap<String, Movies>>
        get() = _genresDataCompleted


    fun fetchData() {
        if (genresLiveData.value != null)
            return
        MovieRepository.isConnected = isConnectedToInternet(context)
        getAllFrontMovies()
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivity: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: Array<NetworkInfo> = connectivity.allNetworkInfo
        for (i in info.iterator())
            if (i.getState() == NetworkInfo.State.CONNECTED) {
                return true
            }

        return false
    }

    private fun getAllFrontMovies() {

        getGenres(context)

        val genresObserver = Observer<Genres> {
            for (genre in it.genres!!) {
                getGenreMovies(genre, 1)
            }
            getSpecialGenreMovies(1)
        }

        val genresDataObserver = Observer<HashMap<String, Movies>> {
            Log.d("ViewModel", "Genres Is Here !!!!!!!")
        }

        genresLiveData.observeForever(genresObserver)

        genresDataCompleted.observeForever(genresDataObserver)

    }

    fun getGenres(context: Context) {
        uiScope.launch {
            _genresLiveData.value = MovieRepository.getAllGenres(context) ?: return@launch
            Log.d("Genres", _genresLiveData.value.toString())
        }
    }

    fun getGenreMovies(genre: Genre, page: Int) {
        uiScope.launch {
            MovieRepository.getGenreMovies(
                genre,
                page,
                genresLiveData.value?.genres?.size!!
            )
            MovieRepository.genresDataCompleted.observeForever(Observer {
                _genresDataCompleted.value = it
            })
        }
    }

    fun getSpecialGenreMovies(page: Int) {
        uiScope.launch {
            MovieRepository.getSpecialGenreMovies(
                page,
                _genresLiveData.value?.genres?.size!!
            )

            MovieRepository.genresDataCompleted.observeForever(Observer {
                _genresDataCompleted.value = it
                Log.d("TAG", "THERE IS NEW DATA HERE !!!!!")
            })


        }
    }


}