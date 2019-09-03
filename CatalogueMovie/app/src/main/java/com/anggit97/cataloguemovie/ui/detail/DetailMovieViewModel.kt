package com.anggit97.cataloguemovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.FakeData

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class DetailMovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var moviewId: Int? = null
    private var movieType: Int? = null
    private var movieList: ArrayList<ResultMovieEntity> = arrayListOf()

    fun getSelectedMovie(): LiveData<ResultMovieEntity>? {
        moviewId?.let { mId ->
            movieType?.let { mType ->
                return movieRepository.getMovieDetail(mType, mId.toString())
            }
            return null
        }
        return null
    }

    fun setMovieId(movieId: Int) {
        this.moviewId = movieId
    }

    fun setMovieType(movieType: Int) {
        this.movieType = movieType
    }
}