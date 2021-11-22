package com.example.spalbumtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.spalbumtask.R
import com.example.spalbumtask.databinding.ActivityMainBinding
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {

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
        viewModel.dataStateLiveData.observe(this, Observer { dataState ->
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
        viewModel.errorScreenVisibility.value = GONE
        recyclerViewAdapter.submitList(albums)
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        viewModel.errorScreenVisibility.value = GONE
        viewModel.progressBarVisibility.value = if(isDisplayed) VISIBLE else GONE
    }

    private fun displayError(message: String?){
        if(message != null) viewModel.errorMessage.value = message else viewModel.errorMessage.value = getString(R.string.generic_message_error)
        viewModel.errorScreenVisibility.value = VISIBLE
    }
}