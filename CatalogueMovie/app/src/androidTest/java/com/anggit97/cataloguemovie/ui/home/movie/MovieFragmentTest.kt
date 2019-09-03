package com.anggit97.cataloguemovie.ui.home.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.testing.SingleFragmentActivity
import com.anggit97.cataloguemovie.utils.EspressoIdlingResource
import com.anggit97.cataloguemovie.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class MovieFragmentTest {

    @get:Rule
    val activitytestRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule(SingleFragmentActivity::class.java)

    @Before
    fun setUp() {
        activitytestRule.activity
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    /**
     * Movie Fragment Test :
     * Periksa apakah recyclerview movies tampil
     * Periksa apakah total item pada recyclerview movies sesuai dengan yang diharapkan(10)
     */
    @Test
    fun name() {
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isCompletelyDisplayed()))
            check(RecyclerViewItemCountAssertion(10))
        }
    }
}