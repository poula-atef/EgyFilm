package com.example.egyfilm.pojo.classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_actors")
data class MovieActors(
    @PrimaryKey
    val id: Long,
    val cast: List<Actor>,
    val crew: List<Crew>
)

@Entity(tableName = "actor")
data class Actor(
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("credit_id") val creditId: String,
    val adult: Boolean,
    val gender: Int,
    @PrimaryKey val id: Long,
    val name: String,
    val popularity: Double,
    val character: String,
    val order: Int
)

@Entity(tableName = "actor_full_data")
@Parcelize
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
    @PrimaryKey val id: Long,
    val name: String,
    val popularity: Double
) : Parcelable

@Entity(tableName = "actor_movie")
data class ActorMovie(
    @Expose(serialize = false, deserialize = false) val actorId: Long,
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
    @PrimaryKey val id: Long,
    val overview: String,
    val popularity: Double,
    val title: String,
    val video: Boolean,
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
    val id: Long,
    val name: String,
    val popularity: Double,
    val department: String,
    val job: String
)

data class ActorMovieCrew (
    @SerializedName("video") var video : Boolean,
    @SerializedName("vote_average") var voteAverage : Double,
    @SerializedName("overview") var overview : String,
    @SerializedName("release_date") var releaseDate : String,
    @SerializedName("vote_count") var voteCount : Int,
    @SerializedName("adult") var adult : Boolean,
    @SerializedName("backdrop_path") var backdropPath : String,
    @SerializedName("title") var title : String,
    @SerializedName("genre_ids") var genreIds : List<Int>,
    @SerializedName("id") var id : Int,
    @SerializedName("original_language") var originalLanguage : String,
    @SerializedName("original_title") var originalTitle : String,
    @SerializedName("poster_path") var posterPath : String,
    @SerializedName("popularity") var popularity : Double,
    @SerializedName("credit_id") var creditId : String,
    @SerializedName("department") var department : String,
    @SerializedName("job") var job : String

)

@Entity(tableName = "actor_movies")
data class ActorMovies(
    @PrimaryKey
    val id: Long,
    val cast: List<ActorMovie>,
    val crew: List<ActorMovieCrew>
)