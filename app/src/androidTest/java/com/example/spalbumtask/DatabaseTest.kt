package com.example.spalbumtask


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.roomdb.AlbumDatabase
import com.example.spalbumtask.util.provideTestList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: AlbumDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AlbumDatabase::class.java
        ).build()
        albumDao = database.getAlbumDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadAlbum() {
        runBlocking {
            albumDao.insert(provideTestList())
            val albumFromDb = albumDao.get()
            assertThat(albumFromDb.size == 1)
            assertThat(albumFromDb[0].id == provideTestList()[0].id)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }
}