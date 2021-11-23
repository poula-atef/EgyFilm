package com.example.egyfilm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentUserDetailsBinding
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.UserDetailsViewModel
import com.google.android.material.snackbar.Snackbar

class UserDetailsFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val actor = UserDetailsFragmentArgs.fromBundle(requireArguments()).actor
        viewModel = ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)
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
            if (it == null)
                Snackbar.make(
                    requireView(), requireContext().getString(R.string.not_saved_movie),
                    Snackbar.LENGTH_SHORT
                ).show()
            if (it?.id == movie.id) {
                Navigation.findNavController(binding.root)
                    .navigate(
                        UserDetailsFragmentDirections.actionUserDetailsFragmentToMovieDetailsFragment(
                            it ?: return@Observer
                        )
                    )
            }
        })
    }


}