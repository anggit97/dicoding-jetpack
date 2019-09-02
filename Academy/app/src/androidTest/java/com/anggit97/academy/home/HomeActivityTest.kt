package com.anggit97.academy.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import com.anggit97.academy.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
    }

    @Test
    fun detailCourseActivityTest() {
        onView(withId(R.id.rv_academy)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        onView(withId(R.id.text_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Menjadi Android Developer Expert")))
        }
    }

    @Test
    fun moveToCourseReaderActivity() {
        onView(withId(R.id.rv_academy)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        onView(withId(R.id.btn_start)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.frame_container)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_module)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }
}