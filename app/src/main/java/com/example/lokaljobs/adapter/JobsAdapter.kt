package com.example.lokaljobs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lokaljobs.R
import com.example.lokaljobs.databinding.ItemViewBinding
import com.example.lokaljobs.network.model.Result

class JobsAdapter(
    private val onClickListener: (Result) -> Unit,
    private val onBookmarkClickListener: (Result, Boolean) -> Unit
) : PagingDataAdapter<Result, JobsAdapter.JobsViewHolder>(DiffUtilCallback()) {

    class DiffUtilCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { result ->
            holder.bind(result)
            holder.itemView.setOnClickListener {
                onClickListener(result)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        return JobsViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onBookmarkClickListener
        )
    }

    class JobsViewHolder(
        private val binding: ItemViewBinding,
        private val onBookmarkClickListener: (Result, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.property = result
            binding.image = result.creatives?.firstOrNull()
            binding.primary = result.primary_details
            binding.jobtag = result.job_tags?.firstOrNull()
            binding.contact = result.contact_preference
            binding.heartBtn.setOnClickListener {
                val newBookmarkState = !result.is_bookmarked
                onBookmarkClickListener(result, newBookmarkState)
            }
            val heartIcon = if (result.is_bookmarked) {
                R.drawable.ic_red_heart
            } else {
                R.drawable.ic_heart
            }
            binding.heartBtn.setImageResource(heartIcon)
            binding.executePendingBindings()
        }
    }
}
