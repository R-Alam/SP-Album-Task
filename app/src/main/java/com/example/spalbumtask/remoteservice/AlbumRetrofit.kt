package com.example.spalbumtask.remoteservice

import com.example.spalbumtask.model.Album
import retrofit2.http.GET

interface AlbumRetrofit {

    @GET("albums")
    suspend fun get() : List<Album>
}