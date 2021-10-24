package com.example.egyfilm.pojo.classes

data class Movies(val page: Int, var results: List<Movie>)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title : String,
    val video : Boolean,
    val vote_average : Double,
    val vote_count : Long
)
