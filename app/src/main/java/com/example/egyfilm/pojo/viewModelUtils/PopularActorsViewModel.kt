package com.example.egyfilm.pojo.viewModelUtils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.egyfilm.pojo.classes.Actor
import com.example.egyfilm.pojo.classes.ActorFullData
import com.example.egyfilm.pojo.classes.PopularActors
import kotlinx.coroutines.launch

class PopularActorsViewModel : ActorDetailsViewModel() {

    private val _allPopularActorsDataLiveData =
        MutableLiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>()
    val allPopularActorsDataLiveData: LiveData<Pair<ArrayList<Actor>, ArrayList<ActorFullData>>?>
        get() = _allPopularActorsDataLiveData


    private val _popularActorsLiveData = MutableLiveData<PopularActors?>()
    val popularActorsLiveData: LiveData<PopularActors?>
        get() = _popularActorsLiveData


    var count = 0
    var isDataSet = false
    private val _popularActorsPagesCount = MutableLiveData<Long?>()
    val popularActorsPagesCount: LiveData<Long?>
        get() = _popularActorsPagesCount

    var currentPage = 1




    fun getPopularActors(page: Int) {
        uiScope.launch {
            _popularActorsLiveData.value = MovieRepository.getPopularActors(page)
        }
    }

    fun getPopularActorsData(page: Int) {
        var actorLst = ArrayList<Actor>()
        var actorFullDataLst = ArrayList<ActorFullData>()
        var actorsCount = 0
        if (!isDataSet) {
            isDataSet = true
            popularActorsLiveData.observeForever(Observer {
                actorLst = ArrayList<Actor>()
                actorFullDataLst = ArrayList<ActorFullData>()
                actorsCount = it?.results?.size ?: return@Observer
                if (_popularActorsPagesCount.value == null)
                    _popularActorsPagesCount.value = it.totalPages
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


    fun donePopularActor() {
        _popularActorsLiveData.value = null
        doneSelectingActor()
    }


}