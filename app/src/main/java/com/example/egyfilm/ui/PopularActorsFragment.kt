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
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.example.egyfilm.pojo.adapters.ActorAdapter
import com.example.egyfilm.pojo.classes.ActorFullData


class PopularActorsFragment : Fragment(), ActorAdapter.OnActorItemClickListener {

    private lateinit var binding: FragmentPopularActorsBinding
    private lateinit var viewModel: MovieViewModel
    private var currentPage = 1
    private var pairList: Pair<ArrayList<Actor>, ArrayList<ActorFullData>> =
        Pair(ArrayList<Actor>(), ArrayList<ActorFullData>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularActorsBinding.inflate(inflater, container, false)

        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        if (viewModel.allPopularActorsDataLiveData.value == null)
            viewModel.getPopularActorsData(currentPage)

        viewModel.allPopularActorsDataLiveData.observe(this.viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer
            pairList.first.addAll(it.first)
            pairList.second.addAll(it.second)
            binding.rec.adapter = null
            val adapter = ActorAdapter(it.first.toSet(), this)
            binding.rec.adapter = adapter
            Log.d("allPopularActorsDataLiveData", "coming Data size is ${it?.first?.size}")
            Log.d(
                "allPopularActorsDataLiveData",
                "coming Data distinct size is ${it?.first?.distinct()!!.size}"
            )
            Log.d(
                "allPopularActorsDataLiveData",
                "coming Data distinct function size is ${distinct(it?.first!!).size}"
            )
            Log.d(
                "allPopularActorsDataLiveData",
                "coming Data set size is ${it?.first?.toSet()!!.size}"
            )

            setupActorPagesNavigation()
            viewModel.donePopularActor()
        })

        return binding.root
    }


    fun distinct(lst: List<Actor>): ArrayList<Actor> {
        val arr = ArrayList<Actor>()
        for (actor in lst) {
            if (!arr.contains(actor))
                arr.add(actor)
        }
        return arr
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
        val actorFullData = pairList.let {
            it.second[it.first.indexOf(actor)]
        }
        Log.d(
            "allPopularActorsDataLiveData",
            "allPopularActorsDataLiveData value is ${viewModel.allPopularActorsDataLiveData.value}"
        )
        Navigation.findNavController(binding.root)
            .navigate(
                PopularActorsFragmentDirections.actionPopularActorsFragmentToUserDetailsFragment(
                    actorFullData
                )
            )
        viewModel.doneSelectingActor()
    }
}

