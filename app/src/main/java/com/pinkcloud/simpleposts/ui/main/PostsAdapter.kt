package com.pinkcloud.simpleposts.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pinkcloud.domain.model.Post
import com.pinkcloud.simpleposts.databinding.PostItemRowBinding

class PostsAdapter(
    private val postClickListener: PostClickListener
): PagingDataAdapter<Post, PostsAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(it, postClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: PostItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post, postClickListener: PostClickListener) {
            binding.post = post
            binding.postClickListener = postClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

interface PostClickListener {
    fun onClick(postId: Int)
}