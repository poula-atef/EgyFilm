package com.example.egyfilm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.egyfilm.R
import com.example.egyfilm.databinding.FragmentMovieDetailsBinding
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.adapters.ActorsAdapter
import com.example.egyfilm.pojo.adapters.GenresAdapter
import com.example.egyfilm.pojo.adapters.MoviesAdapter
import com.example.egyfilm.pojo.classes.*
import com.example.egyfilm.pojo.viewModelUtils.ActorDetailsViewModel
import com.example.egyfilm.pojo.viewModelUtils.MovieDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieDetailsFragment : Fragment(), MoviesAdapter.OnMovieItemClickListener,
    ActorsAdapter.OnActorItemClickListener, GenresAdapter.OnGenreItemClickListener {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var actorDetailsViewModel: ActorDetailsViewModel
    private var movieTrailer: MovieTrailer? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        actorDetailsViewModel = ViewModelProviders.of(this).get(ActorDetailsViewModel::class.java)
        binding.movie = movie
        binding.viewModel = movieDetailsViewModel
        binding.lifecycleOwner = this
        binding.actorRec.adapter = ActorsAdapter(this)
        binding.recommendationsRec.adapter = MoviesAdapter(this)
        binding.similarRec.adapter = MoviesAdapter(this)
        binding.genreRec.adapter = GenresAdapter(this)
        movieDetailsViewModel.getMovieActors(movie.id)
        movieDetailsViewModel.getMovieRelatives(movie.id, 1, Constants.SIMILAR)
        movieDetailsViewModel.getMovieRelatives(movie.id, 1, Constants.RECOMMENDATIONS)
        movieDetailsViewModel.getMovieTrailer(movie.id)

        movieDetailsViewModel.movieTrailerLiveData.observe(this.viewLifecycleOwner, Observer {
            movieTrailer = it.results[0]
        })

        if (FirebaseAuth.getInstance().uid != null) {
            FirebaseDatabase
                .getInstance().getReference("users").child(FirebaseAuth.getInstance().uid!!)
                .child("favs").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (shot in snapshot.children) {
                            if (shot.key?.equals(movie.id.toString())!!) {
                                setFavouriteIcon(R.drawable.ic_heart_filled)
                                return
                            }
                        }
                        setFavouriteIcon(R.drawable.ic_heart)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }

        binding.floatingActionButton.setOnTouchListener { _, event ->
            if (movieTrailer == null) {
                Handler().postDelayed(Runnable {
                    Snackbar.make(
                        requireView(),
                        "There Is No Trailer For This Movie !!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.motionLayout.transitionToState(R.id.start)
                }, 1010)
            } else if (event.action == MotionEvent.ACTION_UP) {
                when (binding.youTubePlayerView.visibility) {
                    View.INVISIBLE -> startVideoTrailer()
                    else -> binding.youTubePlayerView.release()
                }

            }
            false
        }


        binding.loveImg.setOnClickListener {
            if (FirebaseAuth.getInstance().uid == null) {
                val msg: CharSequence = context?.getString(R.string.cant_love_movie).toString()
                Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.loveImg.contentDescription.toString() == R.drawable.ic_heart.toString()
            ) {
                Log.d(
                    "TAG",
                    "content description : " + binding.loveImg.contentDescription.toString()
                )
                Log.d("TAG", "heart img : " + R.drawable.ic_heart.toString())
                Log.d(
                    "TAG",
                    "is equals : ${binding.loveImg.contentDescription.toString() != R.drawable.ic_heart.toString()}"
                )
                FirebaseDatabase
                    .getInstance().getReference("users").child(FirebaseAuth.getInstance().uid!!)
                    .child("favs").child(movie.id.toString()).setValue(movie)
                    .addOnCompleteListener {

                        val msg: CharSequence = if (it.isSuccessful) {
                            setFavouriteIcon(R.drawable.ic_heart_filled)
                            context?.getString(R.string.added_to_favs).toString()
                        } else
                            it.exception?.message.toString()

                        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
                    }
            } else {
                Log.d(
                    "TAG",
                    "content description : " + binding.loveImg.contentDescription.toString()
                )
                Log.d("TAG", "heart img : " + R.drawable.ic_heart.toString())
                Log.d(
                    "TAG",
                    "is equals : ${binding.loveImg.contentDescription.toString() != R.drawable.ic_heart.toString()}"
                )

                FirebaseDatabase
                    .getInstance().getReference("users").child(FirebaseAuth.getInstance().uid!!)
                    .child("favs").child(movie.id.toString()).removeValue().addOnCompleteListener {

                        val msg: CharSequence = if (it.isSuccessful) {
                            setFavouriteIcon(R.drawable.ic_heart)
                            context?.getString(R.string.removed_from_favs).toString()
                        } else
                            it.exception?.message.toString()

                        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
                    }
            }
        }

        return binding.root
    }

    private fun setFavouriteIcon(icon: Int) {
        binding.loveImg.animate().alpha(0f).setDuration(300).start()
        Handler().postDelayed(Runnable {
            binding.loveImg.setImageResource(icon)
            binding.loveImg.contentDescription = icon.toString()
            binding.loveImg.animate().alpha(1f).setDuration(300).start()
        }, 301)
    }

    private fun startVideoTrailer() {
        binding.youTubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (movieTrailer == null)
                    return
                youTubePlayer.loadVideo(movieTrailer?.key!!, 0f)
                youTubePlayer.play()
            }
        })
    }


    override fun onMovieItemClick(movie: Movie) {
        movieDetailsViewModel.getMovieFullDetail(movie.id!!)
        movieDetailsViewModel.selectedMovieLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                        it ?: return@Observer
                    )
                )
            movieDetailsViewModel.doneSelectingMovie()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youTubePlayerView.release()
    }

    override fun onActorItemClick(actor: Actor) {
        actorDetailsViewModel.getActorDetails(actor.id)
        actorDetailsViewModel.actorLiveData.observe(this.viewLifecycleOwner, Observer {
            Navigation.findNavController(binding.root)
                .navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToUserDetailsFragment(
                        it ?: return@Observer
                    )
                )
            actorDetailsViewModel.doneSelectingActor()
        })
    }

    override fun onGenreItemClick(genre: Genre) {
        Navigation.findNavController(binding.root).navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToGenreFragment(
                genre
            )
        )
    }

}