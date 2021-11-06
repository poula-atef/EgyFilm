package com.example.egyfilm.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.egyfilm.databinding.FragmentPopularActorsBinding
import com.example.egyfilm.pojo.adapters.ActorsAdapter
import com.example.egyfilm.pojo.classes.Actor
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieViewModelFactory
import android.widget.ArrayAdapter
import com.example.egyfilm.pojo.adapters.ActorAdapter


class PopularActorsFragment : Fragment(), ActorsAdapter.OnActorItemClickListener {

    private lateinit var binding: FragmentPopularActorsBinding
    private lateinit var viewModel: MovieViewModel
    private var currentPage = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularActorsBinding.inflate(inflater, container, false)

        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        viewModel.getPopularActorsData(currentPage)


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //TODO BOS HNA YA SOLIMAN !!!!!

        viewModel.allPopularActorsDataLiveData.observe(this.viewLifecycleOwner, Observer {

            Log.d("allPopularActorsDataLiveData", "coming Data size is ${it?.first?.size}")
            if(binding.rec.adapter != null)
                Log.d("allPopularActorsDataLiveData", "Data size is ${(binding.rec.adapter as ActorAdapter).itemCount} for page $currentPage")
            val adapter = ActorAdapter(it?.first ?: return@Observer)
            Log.d("allPopularActorsDataLiveData", "Data in adapter size is ${adapter.items.size}")
            binding.rec.adapter = adapter
            setupActorPagesNavigation()
        })

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return binding.root
    }


    private fun setupActorPagesNavigation() {
        if (!viewModel.isDataSet) {
            viewModel.isDataSet = true
            setSpinnerItems()
            setArrowsButtonsClickListener()
            setOnSpinnerItemChangedListener()
        }
    }

    private fun setOnSpinnerItemChangedListener() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pageNumber: Int, p3: Long) {
                if (currentPage != pageNumber + 1) {
                    currentPage = pageNumber + 1
                    viewModel.getPopularActorsData(currentPage)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun setSpinnerItems() {
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item
        )

        for (i in 1..viewModel.popularActorsPagesCount) {
            spinnerAdapter.add(i.toString())
        }

        binding.spinner.adapter = spinnerAdapter
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


    override fun onActorItemClick(actor: Actor) {
    }
}

