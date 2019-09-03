package com.anggit97.cataloguemovie.ui.home.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.ViewModelFactory
import com.anggit97.cataloguemovie.utils.setGone
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var adapter: TvShowAdapter

    private var tvShowList: MutableList<ResultMovieEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            tvShowViewModel = obtainViewModel(it)

            initRecyclerView()

            tvShowViewModel.getTvShow().observe(this, Observer { result ->
                progress_bar.setGone()
                tvShowList = result
                adapter.setMovieList(tvShowList)
            })
        }
    }

    private fun obtainViewModel(fragment: FragmentActivity): TvShowViewModel {
        val factory = ViewModelFactory.getInstance(fragment.application)
        return ViewModelProviders.of(fragment, factory).get(TvShowViewModel::class.java)
    }

    private fun initRecyclerView() {
        rv_tv_show.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = TvShowAdapter()
        rv_tv_show.adapter = adapter
        adapter.setMovieList(tvShowList)
    }

    companion object {

        fun newInstance() = TvShowFragment()
    }
}
