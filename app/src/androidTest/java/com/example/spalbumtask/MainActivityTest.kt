package com.example.spalbumtask

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.spalbumtask.ui.MainActivity
import com.example.spalbumtask.util.DataState
import com.example.spalbumtask.util.provideTestList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

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
    fun testRecyclerViewSuccess(){
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity{ activity ->
            activity.viewModel.dataStateLiveData.value = DataState.Success(provideTestList())
        }

        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(ViewMatchers.withId(R.id.recycler_albums))
            .check(matches(hasDescendant(withText("C Title"))))
    }

    @Test
    fun testViewVisibility(){

        val scenario = launchActivity<MainActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity{ activity ->
            activity.viewModel.dataStateLiveData.value = DataState.Error(Exception("Error"))

        }

        onView(ViewMatchers.withId(R.id.textErrorMessage))
            .check(matches(withText("Error")))
    }
}