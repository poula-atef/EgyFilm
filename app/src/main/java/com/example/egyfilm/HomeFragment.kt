package com.example.egyfilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.egyfilm.databinding.FragmentHomeBinding
import com.example.egyfilm.pojo.MovieViewModel
import com.example.egyfilm.pojo.MoviesAdapter


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : MovieViewModel
    private lateinit var adapter : MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rec.adapter = MoviesAdapter(2)
        getAllFrontMovies()
        return binding.root
    }

    private fun getAllFrontMovies() {

        viewModel.getGenres()

        viewModel.genresLiveData.observe(this.viewLifecycleOwner, Observer {
            for (genre in it.genres) {
                viewModel.getGenreMovies(genre, 1)
            }
            viewModel.getSpecialGenreMovies(1)
        })

        viewModel.genresDataCompleted.observe(this.viewLifecycleOwner, Observer {
/*            for (movies in viewModel.genresMap)
                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")*/
        })

    }

}