package com.anggit97.cataloguemovie.ui.home.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.ViewModelFactory
import com.anggit97.cataloguemovie.utils.setGone
import com.anggit97.cataloguemovie.utils.setVisible
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    private var movieList: MutableList<ResultMovieEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            movieViewModel = obtainViewModel(it)
            initRecyclerView()
            movieViewModel.getMovies().observe(this, Observer { result ->
                progress_bar.setGone()
                movieList = result
                adapter.setMovieList(movieList)
            })
        }
    }

    private fun initRecyclerView() {
        rv_movies.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = MovieAdapter()
        rv_movies.adapter = adapter
        adapter.setMovieList(movieList)
    }

    private fun obtainViewModel(activity: FragmentActivity): MovieViewModel {
        // Use a Factory to inject dependencies into the ViewModel
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(MovieViewModel::class.java)
    }

    companion object {

        fun newInstance() = MovieFragment()
    }
}
