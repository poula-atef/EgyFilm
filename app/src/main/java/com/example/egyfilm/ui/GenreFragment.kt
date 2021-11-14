package com.example.egyfilm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentGenreBinding
import com.example.egyfilm.pojo.adapters.ActorAdapter
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.GenrePageViewModel


@SuppressLint("SetTextI18n")
class GenreFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {

    private lateinit var binding: FragmentGenreBinding
    private lateinit var viewModel: GenrePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(GenrePageViewModel::class.java)

        viewModel.genre = GenreFragmentArgs.fromBundle(requireArguments()).genre

        binding.genreTitle.text = viewModel.genre!!.name.replace('_', ' ')
            .capitalize() + " " + context?.getString(R.string.movies)

        if (viewModel.genresLiveData.value == null)
            viewModel.getGenreMovies()

        viewModel.genresLiveData.observe(viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer
            binding.loading.visibility = View.GONE
            val adapter = MoviesAdapter(this)
            adapter.submitList(it.results!!)
            binding.rec.adapter = adapter

            setupActorPagesNavigation()
        })

        setSpinnerItems()

        return binding.root
    }

    private fun setupActorPagesNavigation() {
        setArrowsButtonsClickListener()
        setOnSpinnerItemChangedListener()
    }

    private fun setOnSpinnerItemChangedListener() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pageNumber: Int, p3: Long) {
                if (viewModel.currentPage != pageNumber + 1) {
                    binding.rec.adapter = null
                    binding.loading.visibility = View.VISIBLE
                    viewModel.currentPage = pageNumber + 1
                    viewModel.getGenreMovies()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun setSpinnerItems() {

        viewModel.genresLiveData.observe(viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer

            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item
            )

            for (i in 1..it.totalPages!!) {
                spinnerAdapter.add(i.toString())
            }

            binding.spinner.adapter = spinnerAdapter
            if (viewModel.currentPage > 0)
                binding.spinner.setSelection(viewModel.currentPage - 1)

        })


    }

    private fun setArrowsButtonsClickListener() {

        binding.frontArrow.setOnClickListener {
            binding.spinner.let { sp ->
                if (sp.selectedItemPosition < sp.adapter.count - 1)
                    sp.setSelection(sp.selectedItemPosition + 1)
            }
        }

        binding.backArrow.setOnClickListener {
            binding.spinner.let { sp ->
                if (sp.selectedItemPosition > 0)
                    sp.setSelection(sp.selectedItemPosition - 1)
            }
        }

    }

    override fun onMovieItemClick(movie: Movie) {
        viewModel.getMovieFullDetail(movie.id!!)
        viewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    GenreFragmentDirections.actionGenreFragmentToMovieDetailsFragment(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingMovie()
        })
    }


}