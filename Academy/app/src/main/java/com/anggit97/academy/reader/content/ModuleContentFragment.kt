package com.anggit97.academy.reader.content


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anggit97.academy.R
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.reader.CourseReaderViewModel
import com.anggit97.academy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_module_content.*


/**
 * A simple [Fragment] subclass.
 */
class ModuleContentFragment : Fragment() {

    private lateinit var readerViewModel: CourseReaderViewModel

    companion object {
        val TAG = ModuleContentFragment::class.java.simpleName

        fun newInstance(): ModuleContentFragment {
            return ModuleContentFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            readerViewModel = obtainViewModel(activity!!)
            readerViewModel.selectedModule.observe(this, Observer { module ->
                progress_bar.visibility = View.GONE
                populateWebView(module)
            })
        }
    }

    private fun populateWebView(content: ModuleEntity?) {
        web_view.loadData(content?.contentEntity?.content, "text/html", "UTF-8")
    }

    private fun obtainViewModel(activity: FragmentActivity): CourseReaderViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel::class.java)
    }
}
