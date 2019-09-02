package com.anggit97.academy.academy

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


class AcademyFragmentTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule(SingleFragmentActivity::class.java)

    private var academyFragment = AcademyFragment()

    @Before
    fun setUp() {
        activityTestRule.activity.setFragment(academyFragment)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
    }

    @Test
    fun academyFragmentTest() {
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).check(RecyclerViewItemCountAssertion(5))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResourceForMainActivity)
    }
}
