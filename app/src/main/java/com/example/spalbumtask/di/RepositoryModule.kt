package com.example.spalbumtask.di

import com.example.spalbumtask.remoteservice.AlbumRetrofit
import com.example.spalbumtask.repository.AlbumRepository
import com.example.spalbumtask.roomdb.AlbumDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAlbumRepository(albumDao: AlbumDao, retrofit: AlbumRetrofit):AlbumRepository{
        return AlbumRepository(albumDao,retrofit)
    }
}
