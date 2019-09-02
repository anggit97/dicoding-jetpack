package com.anggit97.academy.reader.list


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.academy.R
import com.anggit97.academy.adapter.CourseReaderCallback
import com.anggit97.academy.adapter.ModuleListAdapter
import com.anggit97.academy.adapter.ModuleListAdapterListener
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.reader.CourseReaderViewModel
import com.anggit97.academy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_module_list.*


/**
 * A simple [Fragment] subclass.
 */
class ModuleListFragment : Fragment(), ModuleListAdapterListener {

    private var adapter: ModuleListAdapter? = null
    private var courseReaderCallback: CourseReaderCallback? = null

    private lateinit var readerViewModel: CourseReaderViewModel

    companion object {
        fun newInstance(): ModuleListFragment {
            return ModuleListFragment()
        }

        val TAG = ModuleListFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_list, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            readerViewModel = obtainViewModel(activity!!)
            adapter = ModuleListAdapter(this)

            readerViewModel.modules.observe(this, Observer { modules ->
                progress_bar.visibility = View.GONE
                populateRecyclerView(modules)
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderCallback
    }

    override fun onItemClicked(position: Int, moduleId: String?) {
        moduleId?.let {
            courseReaderCallback?.moveTo(position, it)
            readerViewModel.setSelectedModule(moduleId)
        }
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        progress_bar.visibility = View.GONE
        adapter?.setModules(modules)
        rv_module.layoutManager = LinearLayoutManager(context)
        rv_module.setHasFixedSize(true)
        rv_module.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(rv_module.context, DividerItemDecoration.VERTICAL)
        rv_module.addItemDecoration(dividerItemDecoration)
    }

    private fun obtainViewModel(activity: FragmentActivity): CourseReaderViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel::class.java)
    }

}
