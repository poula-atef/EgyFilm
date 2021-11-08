package com.example.egyfilm.pojo.retrofit

import com.example.egyfilm.pojo.classes.*
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
        @Path("movieId") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<MovieFullData>

    // Get Similar Movies List For A Movie
    @GET("movie/{movieId}/{relationship}")
    fun getMovieSimilars(
        @Path("movieId") id: Long,
        @Path("relationship") relationship: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<MovieRelative>

    // Get Actors For Movie
    @GET("movie/{movieId}/credits")
    fun getMovieActors(
        @Path("movieId") id: Long,
        @Query("api_key") apikey: String
    ): Deferred<MovieActors>

    // Get Actor Details
    @GET("person/{personId}")
    fun getActorDetails(
        @Path("personId") id: Long,
        @Query("api_key") apikey: String
    ): Deferred<ActorFullData>


    // Get Actor Movies
    @GET("person/{personId}/movie_credits")
    fun getActorMovies(
        @Path("personId") id: Long,
        @Query("api_key") apikey: String
    ): Deferred<ActorMovies>


    //Get Movie Trailers
    @GET("movie/{movieId}}/videos")
    fun getMovieTrailers(
        @Path("movieId") id: Long,
        @Query("api_key") apiKey: String
    ): Deferred<MovieTrailers>


    //Get Popular Actors
    @GET("person/popular")
    fun getPopularActors(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Deferred<PopularActors>


    //Search For Movie
    @GET("search/movie")
    fun searchForMovie(
        @Query("api_key") apiKey: String,
        @Query("query") name: String,
        @Query("page") page: Int
    ): Deferred<MovieSearch>

}