package com.example.spalbumtask.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spalbumtask.databinding.LayoutListRowBinding
import com.example.spalbumtask.model.Album

class AlbumRecyclerViewAdapter: ListAdapter<Album, AlbumRecyclerViewAdapter.AlbumViewHolder>(Companion) {
    private val _tag = AlbumRecyclerViewAdapter::class.java.simpleName

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
        Log.i(_tag, "Titles = : ${albumItem.title}")
        holder.binding.album = albumItem
        holder.binding.executePendingBindings()
    }
}