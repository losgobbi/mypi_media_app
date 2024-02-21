package com.local.mypi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.local.mypi.databinding.WatchListItemBinding
import com.local.mypi.models.WatchListItem

class WatchListItemAdapter(private val itemClicked: (item: WatchListItem) -> Unit) :
    ListAdapter<WatchListItem, WatchListItemAdapter.ViewHolder>(WatchListComparator()) {

    inner class ViewHolder(binding: WatchListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: WatchListItemBinding

        init {
            this.binding = binding
        }

        fun bind(watchListItem: WatchListItem) {
            binding.txtViewWatchlistItemName.text = watchListItem.name
            binding.deleteItem.setOnClickListener {
                itemClicked(watchListItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WatchListComparator : DiffUtil.ItemCallback<WatchListItem>() {
        override fun areItemsTheSame(oldItem: WatchListItem, newItem: WatchListItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WatchListItem, newItem: WatchListItem): Boolean {
            return oldItem.name == newItem.name
        }
    }
}