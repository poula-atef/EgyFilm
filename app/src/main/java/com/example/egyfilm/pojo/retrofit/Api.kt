package com.example.egyfilm.pojo.retrofit

import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.classes.Movies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("genre/movie/list")
    fun getAllGenres(
        @Query("api_key") apikey: String,
        @Query("language") language: String
    ): Deferred<Genres>


    @GET("discover/movie")
    fun getGenreMovies(
        @Query("api_key") apikey: String,
        @Query("with_genres") genre: Int,
        @Query("page") page: Int
    ): Deferred<Movies>


    @GET("movie/{category}")
    fun getSpecialGenreMovies(
        @Path("category") category: String,
        @Query("api_key") apikey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Movies>


}