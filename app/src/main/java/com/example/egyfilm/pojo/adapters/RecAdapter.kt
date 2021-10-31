package com.example.egyfilm.pojo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.RecItemBinding
import com.example.egyfilm.pojo.classes.Movies

class RecAdapter(private val listener: MoviesAdapter.OnMovieItemClickListener) :
    ListAdapter<Pair<String, Movies>, RecAdapter.RecViewHolder>(RecItemDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        return RecViewHolder.getRecViewHolderInstance(parent, listener)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.bindRecItem(getItem(position), position)
    }

    class RecViewHolder(
        private val binding: RecItemBinding,
        private val listener: MoviesAdapter.OnMovieItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root){
        fun bindRecItem(movieItem: Pair<String, Movies>, position: Int) {
            binding.listener = listener
            if (position == 0)
                binding.itemType = 1
            else
                binding.itemType = 2
            binding.movieIndex = movieItem.first
            binding.movieItems = movieItem.second
            binding.executePendingBindings()
        }

        companion object {
            fun getRecViewHolderInstance(parent: ViewGroup,listener: MoviesAdapter.OnMovieItemClickListener): RecViewHolder {
                val binding =
                    RecItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return RecViewHolder(binding,listener)
            }
        }

    }

    object RecItemDiff : DiffUtil.ItemCallback<Pair<String, Movies>>() {
        override fun areItemsTheSame(
            oldItem: Pair<String, Movies>,
            newItem: Pair<String, Movies>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, Movies>,
            newItem: Pair<String, Movies>
        ): Boolean {
            return oldItem == newItem
        }

    }


}