package com.example.egyfilm.pojo.viewModelUtils

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.retrofit.Api
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.example.egyfilm.pojo.room.MovieDao
import com.example.egyfilm.pojo.room.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


import com.example.egyfilm.pojo.classes.*


object MovieRepository {

    //region Declaration

    private val movieApi: Api = MovieRetrofitApi.getMovieRetrofitApiInstance()
    private lateinit var daoInstance: MovieDao
    private var count = 0
    private val _genresDataCompleted = MutableLiveData<HashMap<String, Movies>>()
    val genresDataCompleted: LiveData<HashMap<String, Movies>>
        get() = _genresDataCompleted
    private val genresMap = HashMap<String, Movies>()
    private val genresList = listOf("top_rated", "popular", "upcoming")
    private val specialGenresSize = genresList.size
    var isConnected = true

    //endregion


    //region Normal Genre Part

    suspend fun getAllGenres(context: Context): Genres? {
        if (!MovieRepository::daoInstance.isInitialized)
            daoInstance = MovieDatabase.getInstance(context).dao

        return withContext(Dispatchers.IO) {
            val response = movieApi.getAllGenres(
                Constants.API_KEY,
                Locale.getDefault().language
            )
            var result: Genres? = null
            try {
                if (isConnected) {
                    result = response.await()
                    if (result.genres?.isNotEmpty() == true)
                        addGenresToDatabase(result.genres!!)
                } else
                    result = Genres()
                result.genres = getGenresFromDatabase()
                Log.d("Repo", "genres are here ${result.genres}")

            } catch (t: Throwable) {
            }
            result
        }
    }

    private fun getGenresFromDatabase(): List<Genre> {
        return daoInstance.getGenres()
    }

    private fun addGenresToDatabase(genres: List<Genre>) {

        daoInstance.deleteGenres()
        for (genre in genres) {
            daoInstance.insertGenre(genre)
        }
    }

    //endregion


    //region Genres Movies

    suspend fun getGenreMovies(genre: Genre, page: Int, genresCount: Int) {
        genresMap[genre.name] = getGenreMoviesSuspend(genre, page) ?: return
        Log.d(
            "Repo",
            "count is $count and now is ${(specialGenresSize + genresCount)}"
        )
        if (count == (specialGenresSize + genresCount))
            doneGettingGenresData()

    }

    private suspend fun getGenreMoviesSuspend(genre: Genre, page: Int): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getGenreMovies(Constants.API_KEY, genre.id, page)
            var result: Movies? = null
            try {
                if (isConnected) {
                    result = response.await()
                    if (result.results?.isNotEmpty() == true) {
                        addGenreMoviesToDatabase(result, genre.name)
                    }
                } else
                    result = Movies()
                result.results = getGenreMoviesFromDatabase(genre.name)
                count++
                Log.d("Repo", "from normal $count")

            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    private fun addGenreMoviesToDatabase(result: Movies, genre: String) {
        result.results?.forEach { movie ->
            movie.category = genre
            daoInstance.insertGenreMovie(movie)
        }
    }

    private fun getGenreMoviesFromDatabase(genre: String): List<Movie> {
        return daoInstance.getGenreMovies(genre)
    }

    private suspend fun doneGettingGenresData() {
        withContext(Dispatchers.Main) {
            _genresDataCompleted.value = genresMap
            Log.d("Repo", "data is ready $genresMap")
        }
    }

    suspend fun getSpecialGenreMovies(page: Int, genresCount: Int) {
        for (genre in genresList) {
            genresMap[genre] = getSpecialGenreMoviesSuspend(genre, page) ?: return
            Log.d(
                "Repo",
                "count is $count and now is ${(specialGenresSize + genresCount)}"
            )
            if (count == (specialGenresSize + genresCount))
                doneGettingGenresData()
        }

    }

    private suspend fun getSpecialGenreMoviesSuspend(
        genre: String,
        page: Int
    ): Movies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getSpecialGenreMovies(
                genre,
                Constants.API_KEY,
                Locale.getDefault().language,
                page
            )
            var result: Movies? = null
            try {
                if (isConnected) {
                    result = response.await()
                    if (result.results?.isNotEmpty() == true)
                        addGenreMoviesToDatabase(result, genre)
                } else
                    result = Movies()
                result.results = getGenreMoviesFromDatabase(genre)
                count++
                Log.d("Repo", "from special $count")
            } catch (t: Throwable) {
                Log.d("ViewModel", t.message!!)
            }
            result
        }
    }

    //endregion


    //region Get Movie Full Details

    suspend fun getMovieFullDetailSuspend(id: Long): MovieFullData? {
        return withContext(Dispatchers.IO) {
            val response =
                movieApi.getMovieFullDetails(id, Constants.API_KEY, Locale.getDefault().language)
            var result: MovieFullData? = null
            try {
                if (isConnected) {
                    result = response.await()
                    removeMovieFullDataToDatabase(id)
                    addMovieFullDataToDatabase(result)
                }
                result = getMovieFullDataFromDatabase(id)
            } catch (t: Throwable) {
            }
            result
        }
    }


    private fun getMovieFullDataFromDatabase(id: Long): MovieFullData? {
        return daoInstance.getMovieFullData(id)
    }

    private fun removeMovieFullDataToDatabase(id: Long) {
        daoInstance.deleteMovieFullData(id)
    }

    private fun addMovieFullDataToDatabase(movie: MovieFullData) {
        daoInstance.insertMovieFullData(movie)
    }

    //endregion


    //region Movie Relatives

    suspend fun getMovieRelativesSuspend(
        id: Long,
        page: Int,
        relationship: String
    ): MovieRelative? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getMovieSimilars(
                id,
                relationship,
                Constants.API_KEY,
                Locale.getDefault().language,
                page
            )
            var result: MovieRelative? = null
            try {
                result = response.await()
                Log.d("TAG", "$relationship + ${result.results}")
            } catch (t: Throwable) {
            }
            result
        }
    }

    //endregion


    //region Movie Actors

    suspend fun getMovieActorsSuspend(id: Long): MovieActors? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getMovieActors(id, Constants.API_KEY)
            var result: MovieActors? = null
            try {
                if (isConnected) {
                    result = response.await()
                    removeMovieActorsFromDatabase(result.id)
                    addMovieActorsToDatabase(result)
                }
                result = getMovieActorsFromDatabase(id)
            } catch (t: Throwable) {
            }
            result
        }
    }

    private fun getMovieActorsFromDatabase(id: Long): MovieActors {
        return daoInstance.getMovieActors(id)
    }

    private fun addMovieActorsToDatabase(movie: MovieActors) {
        daoInstance.insertMovieActors(movie)
    }

    private fun removeMovieActorsFromDatabase(id: Long) {
        daoInstance.deleteMovieActors(id)
    }

    //endregion


    //region Actor Full Data

    suspend fun getActorDetailsSuspend(id: Long): ActorFullData? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getActorDetails(id, Constants.API_KEY)
            var result: ActorFullData? = null
            try {
                if (isConnected) {
                    result = response.await()
                    removeActorFullDetailsFromDatabase(result.id)
                    addActorFullDetailsToDatabase(result)
                }
                result = getActorFullDetailsFromDatabase(id)
            } catch (t: Throwable) {
            }
            result
        }
    }

    private fun getActorFullDetailsFromDatabase(id: Long): ActorFullData {
        return daoInstance.getActorFullData(id)
    }

    private fun addActorFullDetailsToDatabase(actor: ActorFullData) {
        daoInstance.insertActorFullData(actor)
    }

    private fun removeActorFullDetailsFromDatabase(id: Long) {
        daoInstance.deleteActorFullData(id)
    }

    //endregion


    //region Actor Movies

    suspend fun getActorMoviesSuspend(id: Long): ActorMovies? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getActorMovies(id, Constants.API_KEY)
            var result: ActorMovies? = null
            try {
                if (isConnected) {
                    result = response.await()
                    removeActorMoviesFromDatabase(result.id)
                    addActorMoviesToDatabase(result)
                }
                result = getActorMoviesFromDatabase(id)
                Log.d("TAG", result.toString())
            } catch (t: Throwable) {
            }
            result
        }
    }

    private fun getActorMoviesFromDatabase(id: Long): ActorMovies {
        return daoInstance.getActorMovies(id)
    }

    private fun addActorMoviesToDatabase(actorMovies: ActorMovies) {
        daoInstance.insertActorMovie(actorMovies)
    }

    private fun removeActorMoviesFromDatabase(id: Long) {
        daoInstance.deleteActorMovies(id)
    }

    //endregion


    //region Movie Trailer

    suspend fun getMovieTrailers(movieId: Long): MovieTrailers? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getMovieTrailers(movieId, Constants.API_KEY)
            var result: MovieTrailers? = null
            var trailer: MovieTrailer? = null
            try {
                if (isConnected) {
                    result = response.await()
                    if (result.results.isNotEmpty()) {
                        removeMovieTrailerFromDatabase(movieId)
                        trailer = result.results.get(0)
                        trailer.movieId = movieId
                        addMovieTrailerToDatabase(trailer)
                    }
                    result.results = mutableListOf(getMovieTrailerFromDatabase(movieId))
                }
            } catch (t: Throwable) {

            }
            result
        }
    }

    private fun getMovieTrailerFromDatabase(movieId: Long): MovieTrailer {
        return daoInstance.getMovieTrailer(movieId)
    }

    private fun addMovieTrailerToDatabase(movie: MovieTrailer) {
        daoInstance.insertMovieTrailer(movie)
    }

    private fun removeMovieTrailerFromDatabase(movieId: Long) {
        daoInstance.deleteMovieTrailer(movieId)
    }


    //endregion


    //region Popular Actors

    suspend fun getPopularActors(page: Int): PopularActors? {
        return withContext(Dispatchers.IO) {
            val response = movieApi.getPopularActors(
                page,
                Locale.getDefault().language,
                Constants.API_KEY
            )
            var result: PopularActors? = null
            try {
                result = response.await()
            } catch (t: Throwable) {
                
            }
            result
        }
    }

    //endregion


    //region save images and get it to and from room
    /*            movie.posterImage = Glide.with(context).`as`(ByteArray::class.java).load(movie.posterPath).submit().get()
            movie.backdropImage = Glide.with(context).`as`(ByteArray::class.java).load(movie.posterPath).submit().get()
            BitmapDrawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length))
            */
    //endregion

}
