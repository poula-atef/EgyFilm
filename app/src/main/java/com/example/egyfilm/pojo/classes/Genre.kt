package com.example.egyfilm.pojo.classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "genre")
data class Genre(@PrimaryKey val id: Int, val name: String) : Parcelable

data class Genres(var genres: List<Genre>? = null)

