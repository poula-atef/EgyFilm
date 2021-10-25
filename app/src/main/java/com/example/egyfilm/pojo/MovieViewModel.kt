package com.example.egyfilm.pojo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.*
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


    private val _genresDataCompleted = MutableLiveData<HashMap<String,Movies>>()
    val genresDataCompleted: LiveData<HashMap<String,Movies>>
        get() = _genresDataCompleted


    private val _selectedMovieLiveData = MutableLiveData<MovieFullData>()
    val selectedMovieLiveData: LiveData<MovieFullData>
        get() = _selectedMovieLiveData


    private val _movieSimilarsLiveData = MutableLiveData<MovieRelative>()
    val movieSimilarsLiveData: LiveData<MovieRelative>
        get() = _movieSimilarsLiveData


    private val _movieRecommendationsLiveData = MutableLiveData<MovieRelative>()
    val movieRecommendationsLiveData: LiveData<MovieRelative>
        get() = _movieRecommendationsLiveData


    private val _movieActorsLiveData = MutableLiveData<MovieActors>()
    val movieActorsLiveData: LiveData<MovieActors>
        get() = _movieActorsLiveData


    private val _actorLiveData = MutableLiveData<ActorFullData>()
    val actorLiveData: LiveData<ActorFullData>
        get() = _actorLiveData


    private val _actorMoviesLiveData = MutableLiveData<ActorMovies>()
    val actorMoviesLiveData: LiveData<ActorMovies>
        get() = _actorMoviesLiveData


    private val genresList = listOf("top_rated", "popular", "upcoming")
    private val genresMap = HashMap<String, Movies>()
    private var count = 0
    private val specialGenresSize = genresList.size
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val movieApi = MovieRetrofitApi.getMovieRetrofitApiInstance()


    fun getActorMovies(id : Int){
        uiScope.launch {
            _actorMoviesLiveData.value = getActorMoviesSuspend(id)
        }
    }

    private suspend fun getActorMoviesSuspend(id: Int): ActorMovies? {
        return withContext(Dispatchers.IO){
            val response = movieApi.getActorMovies(id,Constants.API_KEY)
            var result : ActorMovies? = null
            try {
                result = response.await()
            }
            catch (t : Throwable){
            }
            result
        }
    }


    //region Get Actor Full Details

    fun getActorDetails(id: Int) {
        uiScope.launch {
            _actorLiveData.value = getActorDetailsSuspend(id)
        }
    }

    private suspend fun getActorDetailsSuspend(id: Int): ActorFullData? {
        return withContext(Dispatchers.IO){
            val response = movieApi.getActorDetails(id,Constants.API_KEY)
            var result : ActorFullData? = null
            try {
                result = response.await()
            }
            catch (t : Throwable){
            }
            result
        }
    }

    //endregion

    //region Get Actors Of A Movie

    fun getMovieActors(id: Int) {
        uiScope.launch {
            _movieActorsLiveData.value = getMovieActorsSuspend(id)
        }
    }

    private suspend fun getMovieActorsSuspend(id: Int): MovieActors? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getMovieActors(id, Constants.API_KEY)
            var result: MovieActors? = null
            try {
                result = response.await()
            } catch (t: Throwable) {
            }
            result
        }
    }

    //endregion

    //region Get Similar & Recommendation Movies For A Movie

    fun getMovieRelatives(id: Int, page: Int, relationship: String) {
        uiScope.launch {
            if (relationship.equals(Constants.RECOMMENDATIONS))
                _movieRecommendationsLiveData.value =
                    getMovieRelativesSuspend(id, page, relationship)
            else
                _movieSimilarsLiveData.value = getMovieRelativesSuspend(id, page, relationship)
        }
    }

    private suspend fun getMovieRelativesSuspend(
        id: Int,
        page: Int,
        relationship: String
    ): MovieRelative? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getMovieSimilars(
                id,
                relationship,
                Constants.API_KEY,
                Locale.getDefault().language,
                page
            )
            var result: MovieRelative? = null
            try {
                result = response.await()
            } catch (t: Throwable) {
            }
            result
        }
    }

    //endregion

    //region Get Movie Full Details

    fun getMovieFullDetail(id: Int) {
        uiScope.launch {
            _selectedMovieLiveData.value = getMovieFullDetailSuspend(id)
        }
    }

    private suspend fun getMovieFullDetailSuspend(id: Int): MovieFullData? {
        return withContext(Dispatchers.IO) {
            val response =
                movieApi.getMovieFullDetails(id, Constants.API_KEY, Locale.getDefault().language)
            var result: MovieFullData? = null
            try {
                result = response.await()
            } catch (t: Throwable) {
            }
            result
        }
    }

    //endregion

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
            _genresDataCompleted.value = genresMap
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