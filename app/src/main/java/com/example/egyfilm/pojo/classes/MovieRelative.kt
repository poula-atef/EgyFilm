package com.example.egyfilm.pojo.classes

import com.google.gson.annotations.SerializedName


data class MovieRelative(
    @SerializedName("total_pages") val totalPages : Int,
    @SerializedName("total_results") val totalResults : Int,
    val page : Int,
    val results : List<Movie>
)