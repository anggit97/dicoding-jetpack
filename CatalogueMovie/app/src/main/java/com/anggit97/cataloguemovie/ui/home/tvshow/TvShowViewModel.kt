package com.anggit97.cataloguemovie.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.FakeData

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class TvShowViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getTvShow(): LiveData<ArrayList<ResultMovieEntity>> {
        return movieRepository.getTvShows()
    }
}