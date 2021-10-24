package com.example.egyfilm.pojo.retrofit

import com.example.egyfilm.pojo.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private object MovieRetrofit {
    fun getMovieRetrofitInstance() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

object MovieRetrofitApi{
    private lateinit var INSTANCE : Api
    fun getMovieRetrofitApiInstance() : Api{
        synchronized(this){
            if(!::INSTANCE.isInitialized){
                INSTANCE = MovieRetrofit.getMovieRetrofitInstance().create(Api::class.java)
            }
            return INSTANCE
        }
    }
}