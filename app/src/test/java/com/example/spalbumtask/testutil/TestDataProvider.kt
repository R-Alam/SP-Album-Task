package com.example.spalbumtask.testutil

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun provideSingleItem(): Flow<DataState<List<Album>>> = flow {
    val testList = listOf<Album>(Album("1", 1, "TestTitle 1"))
    emit(DataState.Success(testList))
}

fun provideMultipleItem(): Flow<DataState<List<Album>>> = flow {
    val testList = listOf<Album>(Album("1", 1, "TestTitle 1"),Album("3", 3, "TestTitle 3"),Album("2", 2, "TestTitle 2"))
    emit(DataState.Success(testList))
}

fun provideError(): Flow<DataState<List<Album>>> = flow {

    emit(DataState.Error(Exception()))
}

fun provideMultiItemList(): List<Album> {
   return listOf<Album>(Album("1", 1, "TestTitle 1"),Album("3", 3, "TestTitle 3"),Album("2", 2, "TestTitle 2"))
}

fun <T> LiveData<T>.getValueForTest(): T? {
    var value: T? = null
    var observer = Observer<T> {
        value = it
    }
    observeForever(observer)
    removeObserver(observer)
    return value
}

