package com.example.egyfilm.pojo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.egyfilm.pojo.classes.*
import kotlinx.coroutines.Deferred

@Dao
interface MovieDao {
    @Query("select * from actor_movies where id =:id")
    fun getActorMovies(id: Long): ActorMovies

    @Query("select * from actor_full_data where id =:id")
    fun getActorFullData(id: Long): ActorFullData

    @Query("select * from actor where id=:id")
    fun getActor(id: Long): Actor

    @Query("select * from genre")
    fun getGenres(): List<Genre>

    @Query("select * from movie where category = :category")
    fun getGenreMovies(category: String): List<Movie>

    @Query("select * from movie_full_data where id =:id")
    fun getMovieFullData(id: Long): MovieFullData?

    @Query("select * from movie_actors where id =:id")
    fun getMovieActors(id: Long): MovieActors


    @Insert
    fun insertActorMovie(movie: ActorMovies)

    @Insert
    fun insertActorFullData(actor: ActorFullData)

    @Insert
    fun insertActor(actor: Actor)

    @Insert
    fun insertGenre(genre: Genre)

    @Insert
    fun insertGenreMovie(movie: Movie)

    @Insert
    fun insertMovieFullData(movie: MovieFullData)

    @Insert
    fun insertMovieActors(movie: MovieActors)


    @Query("delete from actor_movies where id =:id")
    fun deleteActorMovies(id: Long)

    @Query("delete from actor_full_data where id =:id")
    fun deleteActorFullData(id: Long)

    @Query("delete from actor where id=:id")
    fun deleteActor(id: Int)

    @Query("delete from genre")
    fun deleteGenres()

    @Query("delete from movie_full_data where id =:id")
    fun deleteMovieFullData(id: Long)

    @Query("delete from movie_actors where id =:id")
    fun deleteMovieActors(id: Long)

}