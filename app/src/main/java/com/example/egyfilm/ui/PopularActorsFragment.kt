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
import com.example.egyfilm.pojo.classes.Actor
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.egyfilm.pojo.adapters.ActorAdapter
import com.example.egyfilm.pojo.classes.ActorFullData
import com.example.egyfilm.pojo.viewModelUtils.PopularActorsViewModel


class PopularActorsFragment : Fragment(), ActorAdapter.OnActorItemClickListener {

    private lateinit var binding: FragmentPopularActorsBinding
    private lateinit var viewModel: PopularActorsViewModel
    private var pairList: Pair<ArrayList<Actor>, ArrayList<ActorFullData>> =
        Pair(ArrayList<Actor>(), ArrayList<ActorFullData>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularActorsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(PopularActorsViewModel::class.java)

        if (viewModel.allPopularActorsDataLiveData.value == null) {
            viewModel.getPopularActorsData(viewModel.currentPage)
        }

        viewModel.allPopularActorsDataLiveData.observe(this.viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer
            pairList.first.addAll(it.first)
            pairList.second.addAll(it.second)
            binding.loading.visibility = View.GONE
            val adapter = ActorAdapter(it.first.toSet(), this)
            binding.rec.adapter = adapter

            setupActorPagesNavigation()
            viewModel.donePopularActor()
        })
        setSpinnerItems()

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
                    viewModel.getPopularActorsData(viewModel.currentPage)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun setSpinnerItems() {

        viewModel.popularActorsPagesCount.observe(viewLifecycleOwner, Observer {

            if (it == null)
                return@Observer

            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item
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
    }
}

