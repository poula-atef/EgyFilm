package com.example.egyfilm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.egyfilm.databinding.FragmentHomeBinding
import com.example.egyfilm.pojo.MovieViewModel
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.adapters.RecAdapter


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        val connection = isConnectedToInternet()
        MovieViewModel.isConnected = false
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rec.adapter = RecAdapter()
        getAllFrontMovies()
        return binding.root
    }

    private fun getAllFrontMovies() {

        viewModel.getGenres(requireContext())

        viewModel.genresLiveData.observe(this.viewLifecycleOwner, Observer {
            for (genre in it.genres!!) {
                viewModel.getGenreMovies(genre, 1, requireContext())
            }
            viewModel.getSpecialGenreMovies(1, requireContext())
        })

        viewModel.genresDataCompleted.observe(this.viewLifecycleOwner, Observer {
            for (movies in it)
                Log.d("ViewModel", "${movies.key} -----> ${movies.value.results.toString()}")
        })

    }

    fun isConnectedToInternet(): Boolean {
        val connectivity: ConnectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: Array<NetworkInfo> = connectivity.allNetworkInfo
        for (i in info.iterator())
            if (i.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }

        return false;
    }

}