package com.example.spalbumtask.repository

import com.example.spalbumtask.model.Album
import com.example.spalbumtask.remoteservice.AlbumRetrofit
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AlbumRepository constructor(
    private val albumDao: AlbumDao,
    private val albumRetrofit: AlbumRetrofit
) {

    suspend fun getAlbum(): Flow<DataState<List<Album>>> = flow {
        emit(DataState.Loading)
        val albumFromDB = albumDao.get()
        if (albumFromDB.isEmpty()) {
            /**
             * Fetch a list of [Album] from the remote data source
             *
             */

            try {
                val response = albumRetrofit.get()
                if (response.isSuccessful) {
                    val albumFromRemoteSource = response.body()
                    albumFromRemoteSource?.let { albumList ->
                        albumDao.insert(albumList)
                        val sortedAlbum = albumList.sortedBy { it.title }
                        emit(DataState.Success(sortedAlbum))
                    }
                } else {
                    emit(DataState.Error(IOException()))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }

        } else {
            /**
             * Fetch a list of [Album] from the local data source
             *
             */
            val sortedAlbum = albumFromDB.sortedBy { it.title }
            emit(DataState.Success(sortedAlbum))
        }
    }
}