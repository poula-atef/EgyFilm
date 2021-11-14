package com.example.egyfilm.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentFavouriteBinding
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.classes.MovieFullData
import com.example.egyfilm.pojo.classes.User
import com.example.egyfilm.pojo.viewModelUtils.FavouriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavouriteFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(FavouriteViewModel::class.java)
        binding.loading.visibility = View.VISIBLE

        if (viewModel.moviesFullData.isEmpty()) {
            viewModel.getDataFromFirebase()
            viewModel.dataFromFirebase.observe(viewLifecycleOwner, Observer {
                setAdapterMovies()
            })
        } else {
            setAdapterMovies()
        }
        return binding.root
    }

    private fun setAdapterMovies() {
        binding.loading.visibility = View.GONE
        val adapter = MoviesAdapter(this)
        binding.rec.adapter = adapter
        val movies = mutableListOf<Movie>()
        viewModel.moviesFullData.forEach {
            movies.add(Movie(it))
            adapter.submitList(movies)
        }
    }

    override fun onMovieItemClick(movie: Movie) {
        viewModel.moviesFullData.forEach {
            if (movie.id == it.id) {
                Navigation.findNavController(requireView()).navigate(
                    FavouriteFragmentDirections.actionFavouriteFragmentToMovieDetailsFragment(
                        it
                    )
                )
                return@forEach
            }
        }
    }


}