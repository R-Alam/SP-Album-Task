package com.example.spalbumtask.di

import android.content.Context
import androidx.room.Room
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.roomdb.AlbumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAlbumDb(@ApplicationContext context: Context): AlbumDatabase {
        return Room.databaseBuilder(
            context,
            AlbumDatabase:: class.java,
            AlbumDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(albumDatabase: AlbumDatabase) : AlbumDao {
        return albumDatabase.getAlbumDao()
    }
}