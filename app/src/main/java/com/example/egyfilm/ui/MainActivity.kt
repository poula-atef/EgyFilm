package com.example.egyfilm.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.egyfilm.R
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.MovieViewModel
import com.example.egyfilm.pojo.classes.MovieRelative

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel

    //    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

//        getAllFrontMovies()
//        getMovieFullData(438631)
//        getMovieRelatives(438631, 1, Constants.SIMILAR)
//        getMovieRelatives(438631, 1, Constants.RECOMMENDATIONS)
//        getMovieActors(438631)
//        getActorDetails(933238)
//        getActorMovies(933238)

    }

    private fun getActorMovies(id: Int) {
        viewModel.getActorMovies(id)
        viewModel.actorMoviesLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }

    private fun getActorDetails(id: Int) {
        viewModel.getActorDetails(id)
        viewModel.actorLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }

    private fun getMovieActors(id: Int) {
        viewModel.getMovieActors(id)
        viewModel.movieActorsLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }


    private fun getMovieRelatives(id: Int, page: Int, relationship: String) {
        if (relationship.equals(Constants.SIMILAR))
            askAndObserveFor(id, page, relationship, viewModel.movieSimilarsLiveData)
        else
            askAndObserveFor(id, page, relationship, viewModel.movieRecommendationsLiveData)
    }

    private fun askAndObserveFor(
        id: Int,
        page: Int,
        relationship: String,
        liveData: LiveData<MovieRelative>
    ) {
        viewModel.getMovieRelatives(id, page, relationship)
        liveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })

    }

    private fun getMovieFullData(id: Int) {
        viewModel.getMovieFullDetail(id)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this
/*        viewModel.selectedMovieLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
            bining.movieDetails = it
            binding.rating.text = "IMDB ${it.voteAverage}"
        })*/
    }

    private fun getAllFrontMovies() {

        viewModel.getGenres(this)

        viewModel.genresLiveData.observe(this, Observer {
            for (genre in it.genres!!) {
                viewModel.getGenreMovies(genre, 1,this)
            }
            viewModel.getSpecialGenreMovies(1,this)
        })

/*        viewModel.genresDataCompleted.observe(this, Observer {
            for (movies in viewModel.genresMap)
                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")
        })*/

    }


}