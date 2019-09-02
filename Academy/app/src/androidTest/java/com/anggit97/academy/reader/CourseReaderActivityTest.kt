package com.anggit97.academy.reader

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anggit97.academy.R
import com.anggit97.academy.utils.FakeDataDummy
import com.anggit97.academy.utils.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test


class CourseReaderActivityTest {

    private val dummyCourse = FakeDataDummy.generateDummyCourses()[0]

    @get:Rule
    var activityRule: ActivityTestRule<CourseReaderActivity> =
        object : ActivityTestRule<CourseReaderActivity>(CourseReaderActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, CourseReaderActivity::class.java)
                result.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, dummyCourse.courseId)
                return result
            }
        }

//    Memuat Modules:
//
//    Membuka halaman CourseReaderActivity.
//
//    Memastikan RecyclerView sudah tampil.
//
//    Memastikan jumlah item RecyclerView sesuai dengan yang diharapkan.
//
//    Memilih item Modules:
//
//    Membuka halaman CourseReaderActivity.
//
//    Memastikan RecyclerView sudah tampil.
//
//    Memberi aksi klik di item pertama RecyclerView.
//
//    Memastikan WebView sudah tampil.

    @Test
    fun courseReaderTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.frame_container)).apply {
            check(matches(isDisplayed()))
        }
    }

    @Test
    fun modulesTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_module)).apply {
            check(matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(7))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }
}