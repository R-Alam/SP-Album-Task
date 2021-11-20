package com.example.spalbumtask.repository

import android.util.Log
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.remoteservice.AlbumRetrofit
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumRepository constructor(private val albumDao: AlbumDao, private val albumRetrofit: AlbumRetrofit){

    suspend fun getAlbum(): Flow<DataState<List<Album>>> = flow {
        emit(DataState.Loading)

        try {
            val albumFromRemoteSource = albumRetrofit.get()
            for (album in albumFromRemoteSource){
                albumDao.insert(album)
            }
//            val albumFromDB = albumDao.get()
            val sortedAlbum = albumFromRemoteSource.sortedBy { it.title }
            emit(DataState.Success(sortedAlbum))
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("AlbumRepository","ESCATEST : Exception  ${e.printStackTrace()}")
            emit(DataState.Error(e))
        }
    }
}