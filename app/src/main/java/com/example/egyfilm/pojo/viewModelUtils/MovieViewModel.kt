package com.example.egyfilm.pojo.viewModelUtils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.adapters.ActorsAdapter
import com.example.egyfilm.pojo.classes.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MovieViewModel(private var context: Context) : ViewModel() {


    //region Declarations

    private val _genresLiveData = MutableLiveData<Genres>()
    val genresLiveData: LiveData<Genres>
        get() = _genresLiveData


    private val _allPopularActorsDataLiveData =
        MutableLiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>()
    val allPopularActorsDataLiveData: LiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>
        get() = _allPopularActorsDataLiveData


    private val _popularActorsLiveData = MutableLiveData<PopularActors?>()
    val popularActorsLiveData: LiveData<PopularActors?>
        get() = _popularActorsLiveData


    var popularActorsPagesCount: Long = 0
    var isDataSet = false
    private var count = 0


    private val _genreMoviesLiveData = MutableLiveData<Movies>()
    val genreMoviesLiveData: LiveData<Movies>
        get() = _genreMoviesLiveData


    private val _movieTrailerLiveData = MutableLiveData<MovieTrailers>()
    val movieTrailerLiveData: LiveData<MovieTrailers>
        get() = _movieTrailerLiveData

    private val _genresDataCompleted = MutableLiveData<HashMap<String, Movies>>()
    val genresDataCompleted: LiveData<HashMap<String, Movies>>
        get() = _genresDataCompleted


    private val _selectedMovieLiveData = MutableLiveData<MovieFullData?>()
    val selectedMovieLiveData: LiveData<MovieFullData?>
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


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    //endregion

    init {
        Log.d("allPopularActorsDataLiveData", "init is called!!!!")
    }

    fun fetchData() {
        if (genresLiveData.value != null)
            return
        MovieRepository.isConnected = isConnectedToInternet(context)
        getAllFrontMovies()
        Log.d("TAG", "fetchData !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111")
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


    //region Get Actor Movies       Done With Repo

    fun getActorMovies(id: Long) {
        uiScope.launch {
            _actorMoviesLiveData.value = MovieRepository.getActorMoviesSuspend(id)
        }
    }


    //endregion

    //region Get Actor Full Details       Done With Repo

    fun getActorDetails(id: Long) {
        uiScope.launch {
            _actorLiveData.value = MovieRepository.getActorDetailsSuspend(id)
        }
    }


    //endregion

    //region Get Actors Of A Movie       Done With Repo

    fun getMovieActors(id: Long) {
        uiScope.launch {
            _movieActorsLiveData.value = MovieRepository.getMovieActorsSuspend(id)
        }
    }


    //endregion

    //region Get Similar & Recommendation Movies For A Movie       Done With Repo

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

    //endregion

    //region Get Movie Full Details       Done With Repo

    fun getMovieFullDetail(id: Long) {
        uiScope.launch {
            _selectedMovieLiveData.value = MovieRepository.getMovieFullDetailSuspend(id)
        }
    }

    //endregion

    //region Get Special Genre Movies       Done With Repo

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


    //endregion

    //region Get Genre Movies       Done With Repo

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


    //endregion

    //region Get All Genres       Done With Repo

    fun getGenres(context: Context) {
        uiScope.launch {
            _genresLiveData.value = MovieRepository.getAllGenres(context) ?: return@launch
            Log.d("Genres", _genresLiveData.value.toString())
        }
    }

    //endregion

    //region Get Movie Trailers

    fun getMovieTrailer(movieId: Long) {
        uiScope.launch {
            _movieTrailerLiveData.value = MovieRepository.getMovieTrailers(movieId)
        }
    }

    //endregion

    //region Get Popular Actors

    fun getPopularActors(page: Int) {
        uiScope.launch {
            _popularActorsLiveData.value = MovieRepository.getPopularActors(page)
        }
    }

    //endregion

    //region Get Popular Actors

    fun getPopularActorsData(page: Int) {
        val actorLst = ArrayList<Actor>()
        val actorFullDataLst = ArrayList<ActorFullData>()
        var actorsCount = 0
        if (!isDataSet) {
            popularActorsLiveData.observeForever(Observer {
                popularActorsPagesCount = it?.totalPages ?: return@Observer
                actorsCount = it.results.size
                getActorDetails(it.results.get(count).id)
                Log.d("getDataFromApi", "start with index $count")
            })

            actorLiveData.observeForever(Observer {
                actorLst.add(Actor(it ?: return@Observer))
                actorFullDataLst.add(it)
                count += 1
                if (count >= actorsCount) {
                    Log.d("getDataFromApi", "finished at index $count")
                    count = 0
                    _allPopularActorsDataLiveData.value = Pair(actorLst, actorFullDataLst)
                    actorLst.clear()
                    actorFullDataLst.clear()
                } else {
                    Log.d("getDataFromApi", "this is index $count")
                    getActorDetails(
                        popularActorsLiveData.value?.results?.get(count)?.id ?: return@Observer
                    )
                }
            })
        }
        getPopularActors(page)

    }

    //endregion


    fun doneSelectingMovie() {
        _selectedMovieLiveData.value = null
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

    override fun onCleared() {
        super.onCleared()
        Log.d("allPopularActorsDataLiveData", "onCleared Is Called !!!!!!")
        job.cancel()
    }

    fun doneSelectingActor() {
        _actorLiveData.value = null
    }

    fun donePopularActor() {
        _popularActorsLiveData.value = null
        doneSelectingActor()
    }

}