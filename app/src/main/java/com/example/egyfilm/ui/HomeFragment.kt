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
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentHomeBinding
import com.example.egyfilm.pojo.adapters.GenresAdapter
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.adapters.RecAdapter
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Movie


class HomeFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener,
    GenresAdapter.OnGenreItemClickListener, RecAdapter.OnMovieRecyclerViewItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        viewModel.fetchData()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rec.adapter = RecAdapter(this, this)
        binding.genreRec.adapter = GenresAdapter(this)
        binding.actorFab.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToPopularActorsFragment()
            )
        }
        binding.searchFab.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            )
        }
        return binding.root
    }

    override fun onMovieItemClick(movie: Movie) {
        viewModel.getMovieFullDetail(movie.id!!)
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

    override fun onGenreItemClick(genre: Genre) {
        //Toast.makeText(requireContext(), genre.name, Toast.LENGTH_SHORT).show()
        Navigation.findNavController(binding.root).navigate(
            HomeFragmentDirections.actionHomeFragmentToGenreFragment(
                genre
            )
        )
    }

    override fun onClick(name: String) {
//        Toast.makeText(requireContext(), name + " see more !!", Toast.LENGTH_SHORT).show()
        var gen: Genre? = null
        for (genre in viewModel.genresLiveData.value?.genres!!) {
            if (genre.name.equals(name))
                gen = genre
        }
        if (gen == null)
            gen = Genre(-1, name)
        Navigation.findNavController(binding.root).navigate(
            HomeFragmentDirections.actionHomeFragmentToGenreFragment(
                gen
            )
        )
    }

}