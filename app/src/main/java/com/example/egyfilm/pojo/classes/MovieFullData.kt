package com.example.egyfilm.pojo.classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_full_data")
data class MovieFullData(
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("belongs_to_collection") var belongsToCollection: BelongsToCollection? = null,
    @SerializedName("imdb_id") var imdbId: String = "",
    @SerializedName("original_language") var originalLanguage: String = "",
    @SerializedName("original_title") var originalTitle: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("production_companies") var productionCompanies: List<ProductionCompanies>? = null,
    @SerializedName("production_countries") var productionCountries: List<ProductionCountries>? = null,
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("spoken_languages") var spokenLanguages: List<SpokenLanguages>? = null,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Int = 0,
    var adult: Boolean = false,
    var budget: Int = 0,
    var genres: List<Genre>? = null,
    var homepage: String = "",
    @PrimaryKey var id: Long = 0,
    var overview: String = "",
    var popularity: Double = 0.0,
    var revenue: Int = 0,
    var runtime: Int = 0,
    var status: String = "",
    var tagline: String = "",
    var title: String = "",
    var video: Boolean = false

) : Parcelable

@Parcelize
data class BelongsToCollection(
    var id: Long = 0,
    var name: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("backdrop_path") var backdropPath: String = ""
) : Parcelable

@Parcelize
data class ProductionCompanies(
    @SerializedName("logo_path") var logoPath: String = "",
    @SerializedName("origin_country") var originCountry: String = "",
    var id: Long = 0,
    var name: String = ""
) : Parcelable

@Parcelize
data class ProductionCountries(
    @SerializedName("iso_3166_1") var iso31661: String = "",
    var name: String = ""
) : Parcelable

@Parcelize
data class SpokenLanguages(
    @SerializedName("english_name") var englishName: String = "",
    @SerializedName("iso_639_1") var iso6391: String = "",
    var name: String = ""
) : Parcelable