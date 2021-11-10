package com.example.egyfilm.pojo.viewModelUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.egyfilm.pojo.classes.ActorFullData
import kotlinx.coroutines.launch

open class ActorDetailsViewModel : CoroutineViewModel() {
    protected val _actorLiveData = MutableLiveData<ActorFullData>()
    val actorLiveData: LiveData<ActorFullData>
        get() = _actorLiveData


    fun getActorDetails(id: Long) {
        uiScope.launch {
            _actorLiveData.value = MovieRepository.getActorDetailsSuspend(id)
        }
    }

    fun doneSelectingActor() {
        _actorLiveData.value = null
    }

}