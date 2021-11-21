package com.example.spalbumtask.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spalbumtask.model.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albums: List<Album>)

    @Query("SELECT * FROM album")
    suspend fun get():List<Album>

}
