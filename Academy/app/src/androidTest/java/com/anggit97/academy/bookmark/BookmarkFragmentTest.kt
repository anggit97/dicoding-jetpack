package com.anggit97.academy.bookmark

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import com.anggit97.academy.testing.SingleFragmentActivity
import com.anggit97.academy.utils.RecyclerViewItemCountAssertion
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
        activitytestRule.activity.setFragment(bookmarkFragment)
    }

    @Test
    fun bookmarkFragmentTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_bookmark)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(5))
        }
    }
}