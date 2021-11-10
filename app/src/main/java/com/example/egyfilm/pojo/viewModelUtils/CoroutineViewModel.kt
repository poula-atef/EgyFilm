package com.example.egyfilm.pojo.viewModelUtils

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class CoroutineViewModel : ViewModel() {


    protected val job = Job()
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        Log.d("CoroutineViewModel", "onCleared is called!!!!!")
    }

}