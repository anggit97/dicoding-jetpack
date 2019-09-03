package com.anggit97.cataloguemovie.ui.home.tvshow

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
class TvShowFragmentTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule(SingleFragmentActivity::class.java)

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
     * Tv Show Fragment Test Case :
     * - Periksa apakah view_pager tampil
     * - Lakukan aksi  gesture Swipe Kiri pada view pager
     * - Periksa apakah recyclerview tv show tampil
     * - Periksa apakah jumlah item pada recyclerview tv show sesuai dengan yang diharapkan (11)
     */
    @Test
    fun name() {
        onView(withId(R.id.view_pager)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeLeft())
        }
        onView(withId(R.id.rv_tv_show)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(11))
        }
    }
}