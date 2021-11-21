package com.example.spalbumtask.repository

import android.util.Log
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.remoteservice.AlbumRetrofit
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

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
                albumDao.insert(response)
                val sortedAlbum = response.sortedBy { it.title }
                emit(DataState.Success(sortedAlbum))
            } catch (e: Exception) {
                Log.d("AlbumRepository", "ESCATEST : Exception  ********* ${e.printStackTrace()}")
                e.printStackTrace()
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