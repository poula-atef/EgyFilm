package com.example.egyfilm.pojo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.GenreItemBinding
import com.example.egyfilm.pojo.classes.Genre

class GenresAdapter(private val listener: OnGenreItemClickListener) :
    ListAdapter<Genre, GenresAdapter.GenreViewHolder>(GenreItemCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder.createGenreViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindGenreItem(getItem(position))
    }

    class GenreViewHolder(
        private val binding: GenreItemBinding,
        private val listener: OnGenreItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var genre: Genre

        init {
            binding.root.setOnClickListener(this)
        }

        companion object {
            fun createGenreViewHolder(
                parent: ViewGroup,
                listener: OnGenreItemClickListener
            ): GenreViewHolder {
                val binding =
                    GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return GenreViewHolder(binding, listener)
            }
        }


        override fun onClick(p0: View?) {
            listener.onGenreItemClick(genre)
        }

        fun bindGenreItem(item: Genre?) {
            binding.genre = item
            if(item != null)
                genre = item
            binding.executePendingBindings()
        }
    }


    object GenreItemCallBack : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

    }

    interface OnGenreItemClickListener {
        fun onGenreItemClick(genre: Genre)
    }


}