package com.anggit97.academy.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    @Test
    fun detailCourseActivityTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_academy)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }


        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.text_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Menjadi Android Developer Expert")))
        }
    }

    @Test
    fun moveToCourseReaderActivity() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_academy)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }


        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.btn_start)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.frame_container)).check(matches(isDisplayed()))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_module)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }
}