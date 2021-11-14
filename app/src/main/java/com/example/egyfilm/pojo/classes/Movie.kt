package com.example.egyfilm.pojo.classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Movies(
    val page: Int? = null,
    var results: List<Movie>? = null,
    @SerializedName("total_pages") var totalPages: Long? = null,
    @SerializedName("total_results") var totalResults: Long? = null
)

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @Expose(serialize = false, deserialize = false) @PrimaryKey(autoGenerate = true) val dbId: Long,
    @Expose(serialize = false, deserialize = false) var category: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") var genreIds: List<Int>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Long?,
    val adult: Boolean?,
    val id: Long?,
    val overview: String?,
    val popularity: Double?,
    val title: String?,
    val video: Boolean?
) : Parcelable {
    constructor(movie: ActorMovie) :
            this(
                -1,
                "",
                movie.backdropPath,
                movie.genreIds,
                movie.originalLanguage,
                movie.originalTitle,
                movie.posterPath,
                movie.releaseDate,
                movie.voteAverage,
                movie.voteCount,
                movie.adult,
                movie.id,
                movie.overview,
                movie.popularity,
                movie.title,
                movie.video
            )

    constructor(movie: MovieFullData) : this(
        -1,
        "",
        movie.backdropPath,
        null,
        movie.originalLanguage,
        movie.originalTitle,
        movie.posterPath,
        movie.releaseDate,
        movie.voteAverage,
        movie.voteCount.toLong(),
        movie.adult,
        movie.id,
        movie.overview,
        movie.popularity,
        movie.title,
        movie.video
    ){
        val gnrs = mutableListOf<Int>()
        movie.genres.forEach {
            gnrs.add(it.id)
        }
        genreIds = gnrs
    }
}


data class MovieSearch(
    @SerializedName("total_pages") var totalPages: Long,
    @SerializedName("total_results") var totalResults: Int,
    var page: Int,
    var results: List<Movie>
)