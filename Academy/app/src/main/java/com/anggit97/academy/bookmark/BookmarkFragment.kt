package com.anggit97.academy.bookmark


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.academy.R
import com.anggit97.academy.adapter.BookmarkAdapter
import com.anggit97.academy.adapter.BookmarkFragmentCallback
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_bookmark.*


/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    private var adapter: BookmarkAdapter? = null

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private var bookmarks: ArrayList<CourseEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            bookmarkViewModel = obtainViewModel(activity!!)

            adapter = BookmarkAdapter(activity, this)
            rv_bookmark?.layoutManager = LinearLayoutManager(context)
            rv_bookmark?.setHasFixedSize(true)
            rv_bookmark?.adapter = adapter

            bookmarkViewModel.getBookmarks().observe(this, Observer {
                progress_bar.visibility = View.GONE
                adapter?.setListCourses(it)
                adapter?.notifyDataSetChanged()
            })
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(activity)
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(String.format("Segera daftar kelas %s di dicoding.com", course.title))
                .startChooser()
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): BookmarkViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(BookmarkViewModel::class.java)
    }

    companion object {
        fun newInstance(): Fragment {
            return BookmarkFragment()
        }
    }
}
