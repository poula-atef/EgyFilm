package com.example.egyfilm.pojo.classes

import com.google.gson.annotations.SerializedName

data class Movies(val page: Int, var results: List<Movie>)

data class Movie(
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Long,
    val adult: Boolean,
    val id: Long,
    val overview: String,
    val popularity: Double,
    val title: String,
    val video: Boolean
)
