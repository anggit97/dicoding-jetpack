package com.anggit97.cataloguemovie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anggit97.cataloguemovie.BuildConfig
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.*
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_description.*
import kotlinx.android.synthetic.main.content_header.*
import kotlinx.android.synthetic.main.content_score.*

const val EXTRA_ID = "extra_id"
const val EXTRA_TYPE = "extra_type"

class DetailMovieActivity : AppCompatActivity() {

    private var movieId: Int? = null
    private var movieType: Int? = null
    private var movieResult: ResultMovieEntity? = null
    private lateinit var detailMovieViewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        handleExtra()

        initViewModel()

        bindView()

        changeStatusBar()

        onListener()
    }

    private fun changeStatusBar() {
        StatusBarUtil.changeStatusBarColor(this, R.color.colorWhite)
        StatusBarUtil.changeStatusBarItemColor(this, true)
    }

    private fun onListener() {
        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun handleExtra() {
        movieId = intent.getIntExtra(EXTRA_ID, 0)
        movieType = intent.getIntExtra(EXTRA_TYPE, 0)
    }

    private fun initViewModel() {
        detailMovieViewModel = obtainViewModel(this)
        movieId?.let { mId -> detailMovieViewModel.setMovieId(mId) }
        movieType?.let { mType -> detailMovieViewModel.setMovieType(mType) }
        detailMovieViewModel.getSelectedMovie()?.observe(this, Observer { item ->
            progress_bar.setGone()
            movieResult = item
            bindView()
        })
    }

    private fun bindView() {
        movieResult?.let { data ->
            iv_thumbnail.showImageRounded(
                this,
                BuildConfig.BASE_URL_IMAGE.plus(data.backdropPath),
                80
            )
            iv_movies.showImageRounded(this, BuildConfig.BASE_URL_IMAGE.plus(data.backdropPath))
            tv_title_movies.text = data.title
            tv_subtitle_movies.text = data.releaseDate
            tv_score_imdb.text = data.voteAverage.toString().plus("/").plus("10")
            tv_score_popularity.text = data.popularity.toString().plus("%")
            tv_release_date.text = data.releaseDate
            tv_end_date.text = data.releaseDate
            tv_author.text = getString(R.string.author_dummy)
            tv_body_movie.text = data.overview
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailMovieViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(this, factory).get(DetailMovieViewModel::class.java)
    }
}
