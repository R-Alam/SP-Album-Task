package com.example.spalbumtask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spalbumtask.databinding.LayoutListRowBinding
import com.example.spalbumtask.model.Album

/**
 * Recycler view adapter for the [Album] item which uses Jetpack's [ListAdapter] instead of [RecyclerView.Adapter]
 */
class AlbumRecyclerViewAdapter: ListAdapter<Album, AlbumRecyclerViewAdapter.AlbumViewHolder>(Companion) {
    class AlbumViewHolder(val binding:LayoutListRowBinding): RecyclerView.ViewHolder(binding.root)

    companion object:DiffUtil.ItemCallback<Album>(){
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutListRowBinding.inflate(layoutInflater)

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumItem = getItem(position)
        holder.binding.album = albumItem
        holder.binding.executePendingBindings()
    }
}