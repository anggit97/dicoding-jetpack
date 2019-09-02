package com.anggit97.academy.reader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.anggit97.academy.R
import com.anggit97.academy.adapter.CourseReaderCallback
import com.anggit97.academy.reader.content.ModuleContentFragment
import com.anggit97.academy.reader.list.ModuleListFragment
import com.anggit97.academy.viewmodel.ViewModelFactory


class CourseReaderActivity : AppCompatActivity(), CourseReaderCallback {

    private lateinit var courseReaderViewModel: CourseReaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_reader)

        courseReaderViewModel = obtainViewModel(this)

        val bundle = intent.extras
        if (bundle != null) {
            val courseId = bundle.getString(EXTRA_COURSE_ID)
            if (courseId != null) {
                courseReaderViewModel.setCourseId(courseId)
                populateFragment()
            }
        }
    }

    override fun moveTo(position: Int, moduleId: String) {
        val fragment = ModuleContentFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, fragment, ModuleContentFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun populateFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(ModuleListFragment.TAG)
        if (fragment == null) {
            fragment = ModuleListFragment.newInstance()
            fragmentTransaction.add(R.id.frame_container, fragment, ModuleListFragment.TAG)
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    private fun obtainViewModel(activity: FragmentActivity): CourseReaderViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel::class.java)
    }

    companion object {
        const val EXTRA_COURSE_ID = "extra_course_id"
    }
}
