package com.example.egyfilm.pojo.viewModelUtils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.Actor
import com.example.egyfilm.pojo.classes.ActorFullData
import com.example.egyfilm.pojo.classes.PopularActors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PopularActorsViewModel : ViewModel() {

    private val _allPopularActorsDataLiveData =
        MutableLiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>()
    val allPopularActorsDataLiveData: LiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>
        get() = _allPopularActorsDataLiveData


    private val _actorLiveData = MutableLiveData<ActorFullData>()
    val actorLiveData: LiveData<ActorFullData>
        get() = _actorLiveData


    private val _popularActorsLiveData = MutableLiveData<PopularActors?>()
    val popularActorsLiveData: LiveData<PopularActors?>
        get() = _popularActorsLiveData


    var count = 0
    var isDataSet = false
    var popularActorsPagesCount = 0L
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    fun getActorDetails(id: Long) {
        uiScope.launch {
            _actorLiveData.value = MovieRepository.getActorDetailsSuspend(id)
        }
    }

    fun getPopularActors(page: Int) {
        uiScope.launch {
            _popularActorsLiveData.value = MovieRepository.getPopularActors(page)
        }
    }

    fun getPopularActorsData(page: Int) {
        val actorLst = ArrayList<Actor>()
        val actorFullDataLst = ArrayList<ActorFullData>()
        var actorsCount = 0
        if (!isDataSet) {
            actorLst.clear()
            actorFullDataLst.clear()
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

    fun doneSelectingActor() {
        _actorLiveData.value = null
    }


    fun donePopularActor() {
        _popularActorsLiveData.value = null
        doneSelectingActor()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}