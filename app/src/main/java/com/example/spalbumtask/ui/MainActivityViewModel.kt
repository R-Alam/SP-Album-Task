package com.example.spalbumtask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.repository.AlbumRepository
import com.example.spalbumtask.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepository: AlbumRepository): ViewModel(){
    var errorScreenVisibility = MutableLiveData<Int>()
    var errorMessage = MutableLiveData<String>()
    var progressBarVisibility = MutableLiveData<Int>()
    val dataStateLiveData: MutableLiveData<DataState<List<Album>>> = MutableLiveData()


    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetAlbumEvent -> {
                    mainRepository.getAlbum()
                        .onEach {dataState ->
                            dataStateLiveData.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MainStateEvent{

    object GetAlbumEvent: MainStateEvent()

    object None: MainStateEvent()
}