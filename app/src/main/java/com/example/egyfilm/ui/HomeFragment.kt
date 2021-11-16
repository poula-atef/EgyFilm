package com.example.egyfilm.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentHomeBinding
import com.example.egyfilm.pojo.adapters.GenresAdapter
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.adapters.RecAdapter
import com.example.egyfilm.pojo.classes.Genre
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.viewModelUtils.HomeViewModel
import com.example.egyfilm.pojo.viewModelUtils.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener,
    GenresAdapter.OnGenreItemClickListener, RecAdapter.OnMovieRecyclerViewItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val factory = HomeViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.fetchData()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.genresDataCompleted.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            binding.loading.visibility = View.GONE
        })
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

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().clear().apply()
            Navigation.findNavController(binding.root).navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
        }

        binding.userProfile.setOnClickListener {
            if (FirebaseAuth.getInstance().uid == null) {
                val msg: CharSequence = context?.getString(R.string.signup_first).toString()
                Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showDialog()
        }

        return binding.root
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_item)
        dialog.findViewById<TextView>(R.id.username).text =
            PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("name", "?")
        dialog.findViewById<TextView>(R.id.fav_Tv).setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(binding.root)
                .navigate(HomeFragmentDirections.actionHomeFragmentToFavouriteFragment())
        }
        dialog.show()
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