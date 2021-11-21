package com.example.spalbumtask.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.spalbumtask.model.Album
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.spalbumtask", appContext.packageName)
    }

    @Test
    fun verifyInjection() {
        ActivityScenario.launch(MainActivity::class.java).use {
            it.moveToState(Lifecycle.State.CREATED)
            it.onActivity { activity ->
                assertThat(activity.viewModel).isNotNull()
            }
        }
    }

    @Test
    fun testViewVisibility(){
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity{
            it.recyclerViewAdapter = AlbumRecyclerViewAdapter()
            it.recyclerViewAdapter.submitList(getDummyList())
            assertThat(it.recyclerViewAdapter.itemCount).isGreaterThan(0)
            assertThat(it.recyclerViewAdapter.currentList[0].id).isEqualTo(1)
        }
    }

    private fun getDummyList():List<Album>{
        val dummyList = arrayListOf<Album>()
        dummyList.add(Album("1",1,"A Title"))
        dummyList.add(Album("2",2,"B Title"))
        dummyList.add(Album("3",3,"C Title"))
        return dummyList
    }
}