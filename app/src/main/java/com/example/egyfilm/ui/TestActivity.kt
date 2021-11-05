package com.example.egyfilm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.egyfilm.R
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.classes.ActorMovies
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {

                val res = MovieRetrofitApi.getMovieRetrofitApiInstance()
                    .getActorMovies(2524, Constants.API_KEY).await()

                Log.d("TAG", res.toString())

            }
        }

    }

}