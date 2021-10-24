package com.example.egyfilm.pojo.retrofit

import com.example.egyfilm.pojo.classes.Genres
import com.example.egyfilm.pojo.classes.MovieFullData
import com.example.egyfilm.pojo.classes.MovieRelatives
import com.example.egyfilm.pojo.classes.Movies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //Get All Genres
    @GET("genre/movie/list")
    fun getAllGenres(
        @Query("api_key") apikey: String,
        @Query("language") language: String
    ): Deferred<Genres>

    //Get One Genre Movies
    @GET("discover/movie")
    fun getGenreMovies(
        @Query("api_key") apikey: String,
        @Query("with_genres") genre: Int,
        @Query("page") page: Int
    ): Deferred<Movies>

    //Get One Special Genre Movies
    @GET("movie/{category}")
    fun getSpecialGenreMovies(
        @Path("category") category: String,
        @Query("api_key") apikey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Movies>

    //Get Movie Full Details
    @GET("movie/{movieId}")
    fun getMovieFullDetails(
        @Path("movieId") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<MovieFullData>

    // Get Similar Movies List For A Movie
    @GET("movie/{movieId}/{relationship}")
    fun getMovieSimilars(
        @Path("movieId") id: Int,
        @Path("relationship") relationship : String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Deferred<MovieRelatives>

}