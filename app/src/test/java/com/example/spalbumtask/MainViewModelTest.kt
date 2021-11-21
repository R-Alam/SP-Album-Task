package com.example.spalbumtask

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.example.spalbumtask.model.Album
import com.example.spalbumtask.remoteservice.AlbumRetrofit
import com.example.spalbumtask.repository.AlbumRepository
import com.example.spalbumtask.roomdb.AlbumDao
import com.example.spalbumtask.testutil.*
import com.example.spalbumtask.ui.MainActivityViewModel
import com.example.spalbumtask.ui.MainStateEvent
import com.example.spalbumtask.util.DataState
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private lateinit var dataStateObserver: Observer<DataState<List<Album>>>
    private lateinit var viewModel: MainActivityViewModel
    private var repository = mock(AlbumRepository::class.java)

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
//        viewModel = MainActivityViewModel(mock(AlbumRepository::class.java))
        viewModel = MainActivityViewModel(AlbumRepository(mock(AlbumDao::class.java), mock(AlbumRetrofit::class.java)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_view_model(){
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.getAlbum()).thenReturn(provideSingleItem())
            repository.getAlbum().test{
                when(val expectItem = awaitItem()){
                    is DataState.Success<List<Album>> -> {
                        TestCase.assertEquals("Response should have only 1 item", 1, expectItem.data.size);
                    }
                }
                awaitComplete()
            }

            Mockito.`when`(repository.getAlbum()).thenReturn(provideMultipleItem())
            repository.getAlbum().test{
                when(val expectItem = awaitItem()){
                    is DataState.Success<List<Album>> -> {
                        TestCase.assertTrue(expectItem.data.size>1)
                    }
                }
                awaitComplete()
            }

            Mockito.`when`(repository.getAlbum()).thenReturn(provideError())
            repository.getAlbum().test{
                TestCase.assertTrue(awaitItem() is DataState.Error)
                awaitComplete()
            }


        }

    }


//    @ExperimentalCoroutinesApi
//    @Test
//    fun testViewModel(){
//        testCoroutineRule.runBlockingTest {
//
//            val retrofit = mock(AlbumRetrofit::class.java)
//            Mockito.`when`(retrofit.get()).thenReturn(retrofit2.Response.success(arrayListOf(Album("1", 1, "Test data"))))
//            viewModel.setStateEvent(MainStateEvent.GetAlbumEvent)
////            viewModel.dataState.observeForever(dataStateObserver)
//
//            assert(retrofit.get().isSuccessful)
//            TestCase.assertEquals(retrofit.get().body()?.size, 1)
//            TestCase.assertEquals(retrofit.get().body()?.get(0)?.id, 1)
//            when(val dataset = viewModel.dataState.getValueForTest()){
//                is DataState.Success<List<Album>> -> {
//                    assert(dataset.data[0].id == 1)
//                }
//            }
//        }
//    }

}