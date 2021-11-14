package com.example.egyfilm.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.img.animate().alpha(1f).setDuration(600).start()
        Handler().postDelayed(Runnable {
            Navigation.findNavController(binding.root)
                .navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                )
        }, 2000)
        return binding.root
    }
}