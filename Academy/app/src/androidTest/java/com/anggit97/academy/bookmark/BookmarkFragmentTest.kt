package com.anggit97.academy.bookmark

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import com.anggit97.academy.testing.SingleFragmentActivity
import com.anggit97.academy.utils.EspressoIdlingResource
import com.anggit97.academy.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookmarkFragmentTest {

    @get:Rule
    val activitytestRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule(SingleFragmentActivity::class.java)

    val bookmarkFragment = BookmarkFragment()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
        activitytestRule.activity.setFragment(bookmarkFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
    }

    @Test
    fun bookmarkFragmentTest() {
        onView(withId(R.id.rv_bookmark)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(5))
        }
    }
}