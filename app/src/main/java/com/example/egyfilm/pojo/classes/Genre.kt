package com.example.egyfilm.pojo.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(@PrimaryKey val id: Int, val name: String)

data class Genres(var genres: List<Genre>? = null)

