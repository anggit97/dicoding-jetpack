package com.anggit97.academy.bookmark


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.academy.R
import com.anggit97.academy.adapter.BookmarkFragmentCallback
import com.anggit97.academy.adapter.BookmarkPagedAdapter
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.viewmodel.ViewModelFactory
import com.anggit97.academy.vo.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bookmark.*


/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    private var adapter: BookmarkPagedAdapter? = null

    private lateinit var bookmarkViewModel: BookmarkViewModel

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

            adapter = BookmarkPagedAdapter(this)
            rv_bookmark?.layoutManager = LinearLayoutManager(context)
            rv_bookmark?.setHasFixedSize(true)
            rv_bookmark?.adapter = adapter

            bookmarkViewModel.getBookmarks().observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        adapter?.submitList(it.data)
                        adapter?.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(activity, "Terjadi kesalahan", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            })

            itemTouchHelper.attachToRecyclerView(rv_bookmark)
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

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return ItemTouchHelper.Callback.makeMovementFlags(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = adapter?.getItemById(swipedPosition)
                courseEntity?.let {
                    bookmarkViewModel.setBookmark(courseEntity)
                }
                view?.let { view ->
                    val snackbar = Snackbar.make(view, R.string.message_undo, Snackbar.LENGTH_LONG)
                    snackbar.setAction(
                        R.string.message_ok
                    ) {
                        courseEntity?.let {
                            bookmarkViewModel.setBookmark(courseEntity)
                        }
                    }
                    snackbar.show()
                }
            }
        }
    })
}
