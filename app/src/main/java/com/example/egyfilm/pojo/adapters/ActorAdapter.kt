package com.example.egyfilm.pojo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.egyfilm.databinding.ActorItemBinding
import com.example.egyfilm.pojo.classes.Actor

class ActorAdapter(
    private var items: Set<Actor>,
    private val listener: OnActorItemClickListener
) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder.createActorViewHolderInstance(parent, listener)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindViewHolder(items.elementAt(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ActorViewHolder(
        private val binding: ActorItemBinding,
        private val listener: OnActorItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) , View.OnClickListener{

        private lateinit var actor : Actor

        init {
            itemView.setOnClickListener(this)
        }

        companion object {
            fun createActorViewHolderInstance(
                parent: ViewGroup,
                listener: OnActorItemClickListener
            ): ActorViewHolder {
                val binding =
                    ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ActorViewHolder(binding, listener)
            }
        }

        fun bindViewHolder(actor: Actor) {
            binding.actor = actor
            this.actor = actor
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            listener.onActorItemClick(actor)
        }

    }

    interface OnActorItemClickListener {
        fun onActorItemClick(actor: Actor)
    }

}