package com.example.spalbumtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import com.example.spalbumtask.R
import com.example.spalbumtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {

    @get:VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel:MainActivityViewModel by viewModels()
    lateinit var dataBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
    }
}