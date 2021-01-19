package com.lampa.skeleton.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.lampa.skeleton.data.model.domain.post.Post
import com.lampa.skeleton.databinding.ItemPostBinding
import javax.inject.Inject

class PostAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //TODO: base adapter with generic

    private val interaction: Interaction? = null

    private val callback = object : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PostViewHolder(
            ItemPostBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Post>) {
        differ.submitList(list)
    }

    inner class PostViewHolder constructor(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.post = item
            itemView.apply {
                setOnClickListener {
                    interaction?.onItemSelected(bindingAdapterPosition, item)
                }
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Post)
    }
}
