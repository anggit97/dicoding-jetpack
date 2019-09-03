package com.anggit97.cataloguemovie.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.utils.EspressoIdlingResource
import com.anggit97.cataloguemovie.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Anggit Prayogo on 2019-08-28.
 * Github : @anggit97
 */
class HomeActivityTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    private val titleMovieDummy = "Alita: Battle Angel"

    private val titleTvShowDummy = "Naruto The Last"

    @Before
    fun setUp() {
        activityTestRule.activity
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    /**
     * Berpindah ke Detail Movie Activity dari Movie Fragment :
     * - Membuka Home Activity
     * - Memastikan apakah recyclerview movies tampil
     * - Memastikan apakah total item recyclerview sesaui dengan yang diharapkan
     * - Memberikan aksi click item pada recycler view
     * - Membuka halaman Detail Movie
     * - Pastikan judul movies tampil
     * - Pastikan nilai judul movie sesuai dengan yang diharapkan
     */
    @Test
    fun detailMovieActivityTest() {
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(10))
            perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        }

        onView(withId(R.id.tv_title_movies)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(titleMovieDummy)))
        }
    }

    /**
     * Berpindah ke Detail Movie Activity dari Tv Show Fragment :
     * - Membuka Home Activity
     * - Memastikan View Pager Tampil
     * - Memberi aksi swipe kiri pada view pager
     * - Memastikan apakah recyclerview movies tampil
     * - Memastikan apakah total item recyclerview sesaui dengan yang diharapkan
     * - Memberikan aksi click item pada recycler view
     * - Membuka halaman Detail Movie
     * - Pastikan judul movies tampil
     * - Pastikan nilai judul movie sesuai dengan yang diharapkan
     */
    @Test
    fun loadDetailMovieFromTvShowTest() {
        onView(withId(R.id.view_pager)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeLeft())
        }

        onView(withId(R.id.rv_tv_show)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(11))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        onView(withId(R.id.tv_title_movies)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(titleTvShowDummy)))
        }
    }
}