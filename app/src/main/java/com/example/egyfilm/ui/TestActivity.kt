package com.example.egyfilm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.egyfilm.R
import com.example.egyfilm.databinding.ActivityTestBinding
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.classes.ActorMovies
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test)
//        binding.btn.setOnClickListener{
//            binding.youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    youTubePlayer.loadVideo("4qje7Fklj80",0f)
//                }
//            })
//        }


    }

    override fun onStop() {
        super.onStop()
//        binding.youTubePlayerView.release()
    }

}