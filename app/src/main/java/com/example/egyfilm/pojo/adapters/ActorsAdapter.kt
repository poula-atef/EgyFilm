package com.example.egyfilm.pojo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.ActorItemBinding
import com.example.egyfilm.pojo.classes.Actor

class ActorsAdapter(private val listener: OnActorItemClickListener) :
    ListAdapter<Actor, ActorsAdapter.ActorViewHolder>(ActorDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder.createActorViewHolderInstance(parent, listener)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindActorItem(getItem(position))
    }


    class ActorViewHolder private constructor(
        private val binding: ActorItemBinding,
        private val listener: OnActorItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var actor: Actor

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindActorItem(item: Actor?) {
            binding.actor = item
            if (item != null)
                actor = item
            binding.executePendingBindings()
        }

        companion object {
            fun createActorViewHolderInstance(
                parent: ViewGroup,
                listener: OnActorItemClickListener
            ): ActorViewHolder {
                val binding =
                    ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.listener = listener
                return ActorViewHolder(binding, listener)
            }
        }

        override fun onClick(p0: View?) {
            listener.onActorItemClick(actor)
        }
    }

    object ActorDiffCallBack : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

    }

    interface OnActorItemClickListener {
        fun onActorItemClick(actor: Actor)
    }

}