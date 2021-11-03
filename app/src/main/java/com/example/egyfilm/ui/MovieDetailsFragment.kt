package com.example.egyfilm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.egyfilm.databinding.FragmentMovieDetailsBinding
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.adapters.ActorsAdapter
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Actor
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory

class MovieDetailsFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener,
    ActorsAdapter.OnActorItemClickListener {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        val factory = MovieViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        binding.movie = movie
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.actorRec.adapter = ActorsAdapter(this)
        binding.recommendationsRec.adapter = MoviesAdapter(this)
        binding.similarRec.adapter = MoviesAdapter(this)
        viewModel.getMovieRelatives(movie.id, 1, Constants.SIMILAR)
        viewModel.getMovieRelatives(movie.id, 1, Constants.RECOMMENDATIONS)
        viewModel.getMovieActors(movie.id)
        return binding.root
    }

    override fun onMovieItemClick(movie: Movie) {
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_SHORT).show()
    }

    override fun onActorItemClick(actor: Actor) {
        Toast.makeText(requireContext(), actor.originalName, Toast.LENGTH_SHORT).show()
    }

}