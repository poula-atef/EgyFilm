package com.example.egyfilm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.databinding.FragmentMovieDetailsBinding
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.adapters.ActorsAdapter
import com.example.egyfilm.pojo.adapters.GenresAdapter
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Actor
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory

class MovieDetailsFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener,
    ActorsAdapter.OnActorItemClickListener, GenresAdapter.OnGenreItemClickListener {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        binding.movie = movie
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.actorRec.adapter = ActorsAdapter(this)
        binding.recommendationsRec.adapter = MoviesAdapter(this)
        binding.similarRec.adapter = MoviesAdapter(this)
        binding.genreRec.adapter = GenresAdapter(this)
        viewModel.getMovieActors(movie.id)
        viewModel.getMovieRelatives(movie.id, 1, Constants.SIMILAR)
        viewModel.getMovieRelatives(movie.id, 1, Constants.RECOMMENDATIONS)

        return binding.root
    }

    override fun onMovieItemClick(movie: Movie, view: View) {
        viewModel.getMovieFullDetail(movie.id)
        viewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingMovie()
        })
    }

    override fun onActorItemClick(actor: Actor) {
        viewModel.getActorDetails(actor.id)
        viewModel.actorLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToUserDetailsFragment(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingActor()
        })
    }

    override fun onGenreItemClick(genre: Genre) {
        Toast.makeText(requireContext(), genre.name, Toast.LENGTH_SHORT).show()
    }

}