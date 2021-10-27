package com.example.egyfilm.pojo.classes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movies(val page: Int, var results: List<Movie>)

@Entity(tableName = "movie")
data class Movie(
    @Expose(serialize = false, deserialize = false) @PrimaryKey(autoGenerate = true) val dbId: Int,
    @Expose(serialize = false, deserialize = false) val category : String,
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
