package com.anggit97.academy.reader

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anggit97.academy.R
import com.anggit97.academy.adapter.CourseReaderCallback
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.reader.content.ModuleContentFragment
import com.anggit97.academy.reader.list.ModuleListFragment
import com.anggit97.academy.viewmodel.ViewModelFactory
import com.anggit97.academy.vo.Status.*
import kotlinx.android.synthetic.main.activity_course_reader.*


class CourseReaderActivity : AppCompatActivity(), CourseReaderCallback {

    private lateinit var courseReaderViewModel: CourseReaderViewModel
    private var isLarge = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_reader)

        if (frame_list != null) {
            isLarge = true
        }
        courseReaderViewModel = obtainViewModel(this)
        courseReaderViewModel.modules.observe(this, Observer { modules ->
            if (modules != null) {
                when (modules.status) {
                    LOADING -> {
                    }
                    SUCCESS -> {
                        if (modules.data != null && modules.data.isNotEmpty()) {
                            val firstModule = modules.data[0]
                            val isFirstModuleRead = firstModule.read
                            if (!isFirstModuleRead!!) {
                                val firstModuleId = firstModule.moduleId
                                courseReaderViewModel.setSelectedModule(firstModuleId)
                            } else {
                                val lastReadModuleId = modules.data?.let {
                                    getLastReadFromModules(
                                        it
                                    )
                                }
                                if (lastReadModuleId != null) {
                                    courseReaderViewModel.setSelectedModule(lastReadModuleId)
                                }
                            }
                        }
                        removeObserver()
                    }
                    ERROR -> {
                        Toast.makeText(this, "Gagal menampilkan data.", Toast.LENGTH_SHORT).show()
                        removeObserver()
                    }
                }// do nothing
            }
        })

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
        if (!isLarge) {
            val fragment = ModuleContentFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, fragment, ModuleContentFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun removeObserver() {
//        courseReaderViewModel.modules.removeObserver(initObserver)
    }

    private fun populateFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (!isLarge) {
            var fragment = supportFragmentManager.findFragmentByTag(ModuleListFragment.TAG)
            if (fragment == null) {
                fragment = ModuleListFragment.newInstance()
                fragmentTransaction.add(R.id.frame_container, fragment, ModuleListFragment.TAG)
                fragmentTransaction.addToBackStack(null)
            }
            fragmentTransaction.commit()
        } else {
            var fragmentList = supportFragmentManager.findFragmentByTag(ModuleListFragment.TAG)
            if (fragmentList == null) {
                fragmentList = ModuleListFragment.newInstance()
                fragmentTransaction.add(R.id.frame_list, fragmentList, ModuleListFragment.TAG)
            }
            var fragmentContent =
                supportFragmentManager.findFragmentByTag(ModuleContentFragment.TAG)
            if (fragmentContent == null) {
                fragmentContent = ModuleContentFragment.newInstance()
                fragmentTransaction.add(
                    R.id.frame_content,
                    fragmentContent,
                    ModuleContentFragment.TAG
                )
            }
            fragmentTransaction.commit()
        }
    }

    private fun getLastReadFromModules(moduleEntities: List<ModuleEntity>): String? {
        var lastReadModule: String? = null
        for (moduleEntity in moduleEntities) {
            if (moduleEntity.read!!) {
                lastReadModule = moduleEntity.moduleId
                continue
            }
            break
        }
        return lastReadModule
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
