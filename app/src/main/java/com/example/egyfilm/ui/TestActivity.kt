package com.example.egyfilm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.egyfilm.R
import com.example.egyfilm.databinding.ActivityTestBinding
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.classes.ActorMovies
import com.example.egyfilm.pojo.retrofit.MovieRetrofitApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("ClickableViewAccessibility")
class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    private var type = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        binding.switchTv.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (type) {
                    binding.textView5.animate().alpha(0f).setDuration(300).start()
                    binding.switchTv.animate().alpha(0f).setDuration(300).start()


                    Handler().postDelayed(Runnable {
                        runOnUiThread {
                            binding.textView5.text = getString(R.string.login)
                            binding.switchTv.text = getString(R.string.dont_have_account)
                            binding.textView5.animate().alpha(1f).setDuration(300)
                            binding.switchTv.animate().alpha(1f).setDuration(300)
                        }
                    }, 301)

                    type = false
                    Log.d("TAG", "change to login")
                } else {
                    binding.textView5.animate().alpha(0f).setDuration(300).start()
                    binding.switchTv.animate().alpha(0f).setDuration(300).start()


                    Handler().postDelayed(Runnable {
                        runOnUiThread {
                            binding.textView5.text = getString(R.string.signup)
                            binding.switchTv.text = getString(R.string.already_have_account)
                            binding.textView5.animate().alpha(1f).setDuration(300)
                            binding.switchTv.animate().alpha(1f).setDuration(300)
                        }
                    }, 301)

                    type = true
                    Log.d("TAG", "change to signup")
                }
            }
            false
        }
    }


}