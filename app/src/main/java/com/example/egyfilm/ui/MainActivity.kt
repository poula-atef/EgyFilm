package com.example.egyfilm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.egyfilm.R
import com.example.egyfilm.pojo.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

//        getAllFrontMovies()
//        getMovieFullData(438631)
//        getMovieSimilars(438631, 1)

    }

    private fun getMovieSimilars(id: Int, page: Int) {
        viewModel.getMovieSimilars(id, page)
        viewModel.movieSimilarLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }

    private fun getMovieFullData(id: Int) {
        viewModel.getMovieFullDetail(id)
        viewModel.selectedMovieLiveData.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }

    private fun getAllFrontMovies() {

        viewModel.getGenres()

        viewModel.genresLiveData.observe(this, Observer {
            for (genre in it.genres) {
                viewModel.getGenreMovies(genre, 1)
            }
            viewModel.getSpecialGenreMovies(1)
        })

        viewModel.genresDataCompleted.observe(this, Observer {
            for (movies in viewModel.genresMap)
                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")
        })

    }
}