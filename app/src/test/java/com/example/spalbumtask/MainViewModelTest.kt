package com.example.spalbumtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var viewModel: MainActivityViewModel
    private val retrofit = mock(AlbumRetrofit::class.java)
    private val albumDao = mock(AlbumDao::class.java)

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = MainActivityViewModel(AlbumRepository(albumDao, retrofit))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testWhenDBHasNoData(){
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(retrofit.get()).thenReturn(retrofit2.Response.success(provideMultiItemList()))
            Mockito.`when`(albumDao.get()).thenReturn(emptyList())
            viewModel.setStateEvent(MainStateEvent.GetAlbumEvent)

            assert(retrofit.get().isSuccessful)
            when(val dataset = viewModel.dataStateLiveData.getValueForTest()){
                is DataState.Success<List<Album>> -> {
                    assert(dataset.data[0].id == 1)
                    TestCase.assertEquals(dataset.data.size, 3)
                    TestCase.assertEquals(dataset.data[1].id, 2)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testWhenDBHasData(){
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(albumDao.get()).thenReturn(provideMultiItemList())
            viewModel.setStateEvent(MainStateEvent.GetAlbumEvent)

            when(val dataset = viewModel.dataStateLiveData.getValueForTest()){
                is DataState.Success<List<Album>> -> {
                    assert(dataset.data[0].id == 1)
                    TestCase.assertEquals(dataset.data.size, 3)
                    TestCase.assertEquals(dataset.data[1].id, 2)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testWhenNoDataWithError(){
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(retrofit.get()).thenReturn(Response.success(emptyList()))
            Mockito.`when`(retrofit.get().isSuccessful).thenReturn(false)
            Mockito.`when`(albumDao.get()).thenReturn(emptyList())
            viewModel.setStateEvent(MainStateEvent.GetAlbumEvent)

            val result = viewModel.dataStateLiveData.getValueForTest()
            TestCase.assertTrue(result is DataState.Error)
        }
    }

}