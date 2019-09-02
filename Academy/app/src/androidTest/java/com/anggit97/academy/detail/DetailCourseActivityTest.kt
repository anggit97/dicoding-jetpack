package com.anggit97.academy.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import com.anggit97.academy.utils.FakeDataDummy
import com.anggit97.academy.utils.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test

class DetailCourseActivityTest {

    @get:Rule
    val detailCourseActivityTest: ActivityTestRule<DetailCourseActivity> =
        object : ActivityTestRule<DetailCourseActivity>(DetailCourseActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailCourseActivity::class.java)
                result.putExtra(DetailCourseActivity.EXTRA_COURSE, courseEntity.courseId)
                return result
            }
        }


    private val courseEntity = FakeDataDummy.generateDummyCourses()[0]

    @Test
    fun detailModuleTest() {
        onView(withId(R.id.rv_module)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(7))
        }
    }

    @Test
    fun courseTest() {
        onView(withId(R.id.text_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Menjadi Android Developer Expert")))
        }

        onView(withId(R.id.text_date)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(String.format("Deadline %s", courseEntity.deadline))))
        }
    }
}
