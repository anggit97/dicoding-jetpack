package com.anggit97.academy.academy


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.academy.R
import com.anggit97.academy.adapter.AcademyAdapter
import com.anggit97.academy.data.source.local.entity.CourseEntity
import kotlinx.android.synthetic.main.fragment_academy.*
import com.anggit97.academy.viewmodel.ViewModelFactory
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass.
 */
class AcademyFragment : Fragment() {

    private var academyAdapter: AcademyAdapter? = null
    private lateinit var academyViewModel: AcademyViewModel
    private var courses: ArrayList<CourseEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_academy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            academyViewModel = obtainViewModel(activity!!)

            academyAdapter = AcademyAdapter(activity)
            rv_academy?.layoutManager = LinearLayoutManager(context)
            rv_academy?.setHasFixedSize(true)
            rv_academy?.adapter = academyAdapter

            academyViewModel.getCourses().observe(this, Observer {
                progress_bar.visibility = View.GONE
                academyAdapter?.setListCourses(it)
                academyAdapter?.notifyDataSetChanged()
            })
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): AcademyViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(AcademyViewModel::class.java)
    }

    companion object {
        fun newInstance(): Fragment {
            return AcademyFragment()
        }
    }
}
