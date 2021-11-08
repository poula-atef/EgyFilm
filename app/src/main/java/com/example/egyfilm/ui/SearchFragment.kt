package com.example.egyfilm.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.databinding.FragmentSearchBinding
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.Movie

class SearchFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        binding.searchFab.setOnClickListener {
            if (binding.searchEt.text.isEmpty())
                return@setOnClickListener
            binding.loading.visibility = View.VISIBLE

            setComponentVisibility(View.GONE)

            viewModel.getSearchMovies(binding.searchEt.text.toString(), viewModel.currentPage)
        }

        viewModel.searchMoviesLiveData.observe(viewLifecycleOwner, Observer {
            binding.loading.visibility = View.INVISIBLE
            if (it.results.isNotEmpty()) {

                setComponentVisibility(View.VISIBLE)
                binding.searchImg.visibility = View.GONE

                val adapter = MoviesAdapter(this)
                binding.rec.adapter = adapter
                adapter.submitList(it.results)
                setupActorPagesNavigation()
            } else {
                binding.searchImg.visibility = View.VISIBLE
            }
        })
        setSpinnerItems()

        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchText = text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.searchEt.setText(viewModel.searchText)
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
                    binding.spinner.adapter = null
                    binding.loading.visibility = View.VISIBLE
                    viewModel.currentPage = pageNumber + 1
                    viewModel.getSearchMovies(viewModel.searchText, viewModel.currentPage)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun setSpinnerItems() {

        viewModel.searchMoviesPagesCount.observe(viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer

            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                R.layout.simple_spinner_item
            )

            for (i in 1..it) {
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

    private fun setComponentVisibility(vis: Int) {
        binding.rec.visibility = vis
        binding.spinner.visibility = vis
        binding.frontArrow.visibility = vis
        binding.backArrow.visibility = vis
    }

    override fun onMovieItemClick(movie: Movie, view: View) {
        viewModel.getMovieFullDetail(movie.id!!)
        viewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(
                        it ?: return@Observer
                    )
                )
            viewModel.doneSelectingMovie()
        })
    }


}