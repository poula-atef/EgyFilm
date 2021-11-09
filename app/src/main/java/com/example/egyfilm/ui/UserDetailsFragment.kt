package com.example.egyfilm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.databinding.FragmentUserDetailsBinding
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory

class UserDetailsFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val actor = UserDetailsFragmentArgs.fromBundle(requireArguments()).actor
        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.actorRec.adapter = MoviesAdapter(this)
        binding.actor = actor
        viewModel.getActorMovies(actor.id)
        return binding.root
    }

    override fun onMovieItemClick(movie: Movie) {
        viewModel.getMovieFullDetail(movie.id!!)
        viewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    UserDetailsFragmentDirections.actionUserDetailsFragmentToMovieDetailsFragment(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingMovie()
        })
    }

}