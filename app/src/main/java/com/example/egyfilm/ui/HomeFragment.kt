package com.example.egyfilm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.databinding.FragmentHomeBinding
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.adapters.RecAdapter
import com.example.egyfilm.pojo.classes.Movie


class HomeFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rec.adapter = RecAdapter(this)
        return binding.root
    }

/*    private fun getAllFrontMovies() {

        viewModel.getGenres(requireContext())

        viewModel.genresLiveData.observe(this.viewLifecycleOwner, Observer {
            for (genre in it.genres!!) {
                viewModel.getGenreMovies(genre, 1)
            }
            viewModel.getSpecialGenreMovies(1)
        })

        viewModel.genresDataCompleted.observe(this.viewLifecycleOwner, Observer {
            for (movies in it)
                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")
        })

    }
*/


    override fun onMovieItemClick(movie: Movie) {
        viewModel.getMovieFullDetail(movie.id)
        viewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingMovie()
        })
    }

}