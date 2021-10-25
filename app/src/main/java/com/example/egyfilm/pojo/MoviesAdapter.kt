package com.example.egyfilm.pojo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.LargeMovieItemBinding
import com.example.egyfilm.databinding.SmallMovieItemBinding
import com.example.egyfilm.pojo.classes.Movie

class MoviesAdapter(private val type: Int) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (type == 1)
            return LargeMovieViewHolder.getMovieViewHolderInstance(parent)
        return SmallMovieViewHolder.getMovieViewHolderInstance(parent)
    }

    override fun onBindViewHolder(holderSmall: RecyclerView.ViewHolder, position: Int) {
        (holderSmall as LargeMovieViewHolder).bindMovieItem(getItem(position))
    }

    class SmallMovieViewHolder private constructor(private val binding: SmallMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovieItem(movieItem: Movie) {
            binding.movieItem = movieItem
            binding.executePendingBindings()
        }

        companion object {
            fun getMovieViewHolderInstance(parent: ViewGroup): SmallMovieViewHolder {
                val binding =
                    SmallMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SmallMovieViewHolder(binding)
            }
        }
    }


    class LargeMovieViewHolder private constructor(private val binding: LargeMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovieItem(movieItem: Movie) {
            binding.movieItem = movieItem
            binding.executePendingBindings()
        }

        companion object {
            fun getMovieViewHolderInstance(parent: ViewGroup): LargeMovieViewHolder {
                val binding =
                    LargeMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return LargeMovieViewHolder(binding)
            }
        }
    }


    object MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}