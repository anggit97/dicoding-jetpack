package com.anggit97.cataloguemovie.ui.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.utils.EspressoIdlingResource
import com.anggit97.cataloguemovie.utils.FakeData
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Anggit Prayogo on 2019-08-28.
 * Github : @anggit97
 */
class DetailMovieActivityTest {

    private val movieEntity = FakeData.generateMovies()[0]
    private val titleDummy = "Alita: Battle Angel"
    private val releaseDateDummy = "February 5, 2019"

    @get:Rule
    val detailMovieActivity: ActivityTestRule<DetailMovieActivity> =
        object : ActivityTestRule<DetailMovieActivity>(DetailMovieActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val intent = Intent(targetContext, DetailMovieActivity::class.java)
                intent.putExtra(EXTRA_ID, movieEntity.id)
                intent.putExtra(EXTRA_TYPE, 1)
                return intent
            }
        }

    @Before
    fun setUp() {
        detailMovieActivity.activity
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    /**
     * Memuat Detail Movie Activity Test
     * a) Memberikan
     *  - Membuka halaman detail movie activity
     *  - memastikan TextView tv_title_movies tampil
     *  - memastikan nilai title sesuai dengan yang diharapkan
     *  - memastikan TextView Release Date Tampil
     *  - memastikan nilai dari release date sesuai dengan yang diharapkan
     */
    @Test
    fun loadMovieTest() {
        onView(withId(R.id.tv_title_movies)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(titleDummy)))
        }

        onView(withId(R.id.tv_release_date)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(releaseDateDummy)))
        }
    }
}