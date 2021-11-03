package com.example.egyfilm.pojo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.LargeMovieItemBinding
import com.example.egyfilm.databinding.SmallMovieItemBinding
import com.example.egyfilm.pojo.classes.Movie

class MoviesAdapter(private val listener: OnMovieItemClickListener) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallBack) {

    var type: Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (type == 1)
            LargeMovieViewHolder.getMovieViewHolderInstance(parent, listener)
        else
            SmallMovieViewHolder.getMovieViewHolderInstance(parent, listener)
    }


    override fun onBindViewHolder(holderSmall: RecyclerView.ViewHolder, position: Int) {
        if (type == 1)
            (holderSmall as LargeMovieViewHolder).bindMovieItem(getItem(position))
        else
            (holderSmall as SmallMovieViewHolder).bindMovieItem(getItem(position))

    }

    class SmallMovieViewHolder private constructor(
        private val binding: SmallMovieItemBinding,
        private val listener: OnMovieItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var movie: Movie
        fun bindMovieItem(movieItem: Movie) {
            movie = movieItem
            binding.root.setOnClickListener(this)
            binding.movieItem = movieItem
            binding.executePendingBindings()
        }

        companion object {
            fun getMovieViewHolderInstance(
                parent: ViewGroup,
                listener: OnMovieItemClickListener
            ): SmallMovieViewHolder {
                val binding =
                    SmallMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SmallMovieViewHolder(binding, listener)
            }
        }

        override fun onClick(p0: View?) {
            listener.onMovieItemClick(movie, itemView)
        }
    }


    class LargeMovieViewHolder private constructor(
        private val binding: LargeMovieItemBinding,
        private val listener: OnMovieItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var movie: Movie
        fun bindMovieItem(movieItem: Movie) {
            movie = movieItem
            binding.root.setOnClickListener(this)
            binding.movieItem = movieItem
            binding.executePendingBindings()
        }

        companion object {
            fun getMovieViewHolderInstance(
                parent: ViewGroup,
                listener: OnMovieItemClickListener
            ): LargeMovieViewHolder {
                val binding =
                    LargeMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return LargeMovieViewHolder(binding, listener)
            }
        }

        override fun onClick(p0: View?) {
            listener.onMovieItemClick(movie, itemView)
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


    interface OnMovieItemClickListener {
        fun onMovieItemClick(movie: Movie, view: View)
    }

}