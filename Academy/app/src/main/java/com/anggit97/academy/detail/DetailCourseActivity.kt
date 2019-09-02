package com.anggit97.academy.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.academy.R
import com.anggit97.academy.adapter.DetailCourseAdapter
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.reader.CourseReaderActivity
import com.anggit97.academy.utils.GlideApp
import com.anggit97.academy.viewmodel.ViewModelFactory
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_course.*
import kotlinx.android.synthetic.main.content_detail_course.*


class DetailCourseActivity : AppCompatActivity() {

    private var adapter: DetailCourseAdapter? = null
    private lateinit var detailCourseViewModel: DetailCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_course)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = DetailCourseAdapter()

        detailCourseViewModel = obtainViewModel(this)

        val extras = intent.extras
        if (extras != null) {
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null) {
                detailCourseViewModel.setCourseId(courseId)

                detailCourseViewModel.getModules().observe(this, Observer {
                    progress_bar.visibility = View.GONE
                    adapter?.setModules(it)
                    adapter?.notifyDataSetChanged()
                })

                detailCourseViewModel.getCourse().observe(this, Observer {
                    it?.let {
                        populateCourse(it)
                    }
                })
            }
        }

        rv_module.isNestedScrollingEnabled = false
        rv_module.layoutManager = LinearLayoutManager(this)
        rv_module.setHasFixedSize(true)
        rv_module.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(rv_module.context, DividerItemDecoration.VERTICAL)
        rv_module.addItemDecoration(dividerItemDecoration)
    }

    private fun populateCourse(course: CourseEntity) {
        text_title.text = course.title
        text_desc.text = course.description
        text_date.text = String.format("Deadline %s", course.deadline)

        GlideApp.with(applicationContext)
            .load(course.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(image_poster)

        btn_start.setOnClickListener { v ->
            val intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java)
            intent.putExtra(
                CourseReaderActivity.EXTRA_COURSE_ID,
                detailCourseViewModel.getCourseId()
            )
            v.context.startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailCourseViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(DetailCourseViewModel::class.java)
    }

    companion object {
        const val EXTRA_COURSE: String = "extra_course"
    }

}
