package com.example.egyfilm.pojo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.egyfilm.pojo.classes.*
import kotlinx.coroutines.Deferred

@Dao
interface MovieDao {
    @Query("select * from actor_movie where actorId =:id")
    fun getActorMovies(id: Int): LiveData<List<ActorMovie>>

    @Query("select * from actor_full_data where id =:id")
    fun getActorFullData(id: Int): ActorFullData

    @Query("select * from actor where id=:id")
    fun getActor(id: Int): Actor

    @Query("select * from genre")
    fun getGenres(): Deferred<List<Genre>>

    @Query("select * from movie where genreIds like '%' || :id || '%'")
    fun getGenreMovies(id: Int) : List<Movie>



    @Insert
    fun insertActorMovie(movie: ActorMovie)

    @Insert
    fun insertActorFullData(actor: ActorFullData)

    @Insert
    fun insertActor(actor: Actor)

    @Insert
    fun insertGenre(genre: Genre)

    @Insert
    fun insertGenreMovie(movie : Movie)



    @Query("delete from actor_movie where actorId =:id")
    fun deleteActorMovies(id: Int)

    @Query("delete from actor_full_data where id =:id")
    fun deleteActorFullData(id: Int)

    @Query("delete from actor where id=:id")
    fun deleteActor(id: Int)

    @Query("delete from genre")
    fun deleteGenres()


}