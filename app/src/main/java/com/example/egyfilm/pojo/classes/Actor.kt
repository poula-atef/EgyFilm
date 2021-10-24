package com.example.egyfilm.pojo.classes

import com.google.gson.annotations.SerializedName

data class MovieActors(
    val id: Int,
    val cast: List<Actor>,
    val crew: List<Crew>
)

data class Actor(
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("credit_id") val creditId: String,
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val name: String,
    val popularity: Double,
    val character: String,
    val order: Int
)

data class Crew(
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("credit_id") val creditId: String,
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val name: String,
    val popularity: Double,
    val department: String,
    val job: String
)

data class ActorFullData(
    @SerializedName("also_known_as") val alsoKnownAs: List<String>,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("profile_path") val profilePath: String,
    val adult: Boolean,
    val biography: String,
    val birthday: String,
    val deathday: String,
    val gender: Int,
    val homepage: String,
    val id: Int,
    val name: String,
    val popularity: Double
)

data class ActorMovie(

    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Long,
    @SerializedName("credit_id") val creditId: String,
    val adult: Boolean,
    val id: Long,
    val overview: String,
    val popularity: Double,
    val title: String,
    val video: Boolean,
    val character: String,
    val order: Int

)

data class ActorMovies(
    val cast: List<ActorMovie>,
    val crew: List<String>,
    val id: Int
)