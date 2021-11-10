package com.example.egyfilm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.egyfilm.R

class MainActivity : AppCompatActivity() {
//    private lateinit var viewModel: MovieViewModel

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
//
//    private fun getActorMovies(id: Long) {
//        viewModel.getActorMovies(id)
//        viewModel.actorMoviesLiveData.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//        })
//    }
//
//    private fun getActorDetails(id: Long) {
//        viewModel.getActorDetails(id)
//        viewModel.actorLiveData.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//        })
//    }
//
//    private fun getMovieActors(id: Long) {
//        viewModel.getMovieActors(id)
//        viewModel.movieActorsLiveData.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//        })
//    }
//
//
//    private fun getMovieRelatives(id: Long, page: Int, relationship: String) {
//        if (relationship.equals(Constants.SIMILAR))
//            askAndObserveFor(id, page, relationship, viewModel.movieSimilarsLiveData)
//        else
//            askAndObserveFor(id, page, relationship, viewModel.movieRecommendationsLiveData)
//    }
//
//    private fun askAndObserveFor(
//        id: Long,
//        page: Int,
//        relationship: String,
//        liveData: LiveData<MovieRelative>
//    ) {
//        viewModel.getMovieRelatives(id, page, relationship)
//        liveData.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//        })
//
//    }
//
//    private fun getMovieFullData(id: Long) {
//        viewModel.getMovieFullDetail(id)
////        binding.viewModel = viewModel
////        binding.lifecycleOwner = this
///*        viewModel.selectedMovieLiveData.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//            bining.movieDetails = it
//            binding.rating.text = "IMDB ${it.voteAverage}"
//        })*/
//    }
//
//    private fun getAllFrontMovies() {
//
//        viewModel.getGenres(this)
//
//        viewModel.genresLiveData.observe(this, Observer {
//            for (genre in it.genres!!) {
//                viewModel.getGenreMovies(genre, 1)
//            }
//            viewModel.getSpecialGenreMovies(1)
//        })
//
///*        viewModel.genresDataCompleted.observe(this, Observer {
//            for (movies in viewModel.genresMap)
//                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")
//        })*/
//
//    }


}