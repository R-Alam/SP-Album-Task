package com.example.spalbumtask.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spalbumtask.model.Album

@Database(entities = [Album::class ], version = 1)
abstract class AlbumDatabase: RoomDatabase() {

    abstract fun getAlbumDao(): AlbumDao

    companion object{
        const val DATABASE_NAME: String = "album_db"
    }


}