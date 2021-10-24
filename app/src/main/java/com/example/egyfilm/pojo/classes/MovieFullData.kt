package com.example.egyfilm.pojo.classes

import com.google.gson.annotations.SerializedName

data class MovieFullData(
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollection,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountries>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages>,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val adult: Boolean,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean

)

data class BelongsToCollection(
    val id: Int,
    val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String
)

data class ProductionCompanies(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("origin_country") val originCountry: String,
    val id: Int,
    val name: String
)

data class ProductionCountries(
    @SerializedName("iso_3166_1") val iso31661: String,
    val name: String
)

data class SpokenLanguages(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso6391: String,
    val name: String
)