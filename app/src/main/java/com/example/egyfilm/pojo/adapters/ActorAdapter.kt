package com.example.egyfilm.pojo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.ActorItemBinding
import com.example.egyfilm.pojo.classes.Actor

class ActorAdapter(var items: ArrayList<Actor>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(private val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        companion object {
//            fun createActorViewHolderInstance(parent: ViewGroup): ActorViewHolder {
//                val binding =
//                    ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                return ActorViewHolder(binding)
//            }
//        }

        fun bindViewHolder(actor: Actor) {
            binding.actor = actor
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding =
            ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindViewHolder(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

}