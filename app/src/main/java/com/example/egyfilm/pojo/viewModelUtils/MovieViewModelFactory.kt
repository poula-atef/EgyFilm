package com.example.egyfilm.pojo.viewModelUtils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java))
            return MovieViewModel(context) as T
        throw IllegalAccessError("Unknown ViewModel!!!")
    }
}