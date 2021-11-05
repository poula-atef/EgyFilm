package com.example.egyfilm.pojo.classes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieTrailers(
    var id: Long,
    var results: List<MovieTrailer>
)

@Entity(tableName = "movie_trailer")
data class MovieTrailer(
    @Expose(serialize = false, deserialize = false) @PrimaryKey var movieId: Long,
    @SerializedName("iso_639_1") var iso6391: String,
    @SerializedName("iso_3166_1") var iso31661: String,
    var name: String,
    var key: String,
    var site: String,
    var size: Int,
    var type: String,
    var official: Boolean,
    var publishedAt: String,
    var id: String

)