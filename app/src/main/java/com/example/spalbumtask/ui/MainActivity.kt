package com.example.spalbumtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.spalbumtask.R
import com.example.spalbumtask.databinding.ActivityMainBinding
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {

//    @get:VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel:MainActivityViewModel by viewModels()
    lateinit var dataBinding:ActivityMainBinding
    lateinit var recyclerViewAdapter : AlbumRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel
        recyclerViewAdapter = AlbumRecyclerViewAdapter()
        dataBinding.adapter = recyclerViewAdapter
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetAlbumEvent)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<Album>> -> {
                    displayProgressBar(false)
                    updateUI(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun updateUI(albums: List<Album>){
        Log.d("MainActivity","ESCATEST : Titles  count = " + albums.size)
        recyclerViewAdapter.submitList(albums)
    }

    private fun displayProgressBar(isDisplayed: Boolean){
//        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){
//        if(message != null) viewModel.title = message else viewModel.title = "Unknown error."
    }
}