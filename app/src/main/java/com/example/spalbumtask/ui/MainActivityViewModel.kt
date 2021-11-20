package com.example.spalbumtask.ui

import android.util.Log
import androidx.lifecycle.*
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
    private val _dataState: MutableLiveData<DataState<List<Album>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Album>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetAlbumEvent ->{
                    mainRepository.getAlbum()
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                else -> {

                    Log.d("MainActivity","ESCATEST : Failed ")
                }
            }

        }
    }
}

sealed class MainStateEvent{

    object GetAlbumEvent: MainStateEvent()

    object None: MainStateEvent()
}