package com.anggit97.myidlingresource

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun checkTest() {
        onView(withId(R.id.text_view)).check(
            matches(
                withText(
                    activityTestRule.activity.resources.getString(
                        R.string.prepare
                    )
                )
            )
        )
        onView(withId(R.id.button)).perform(click())

//        onView(withId(R.id.text_view))
//            .check(matches(withText(activityTestRule.activity.resources.getString(R.string.delay1))))

        onView(withId(R.id.text_view))
            .check(matches(withText(activityTestRule.activity.resources.getString(R.string.delay2))))
    }
}
