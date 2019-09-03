package com.anggit97.cataloguemovie.data

import androidx.lifecycle.LiveData
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
interface MovieDataSource {

    fun getMovies(): LiveData<ArrayList<ResultMovieEntity>>

    fun getTvShows(): LiveData<ArrayList<ResultMovieEntity>>

    fun getMovieDetail(movieType: Int, movieId: String) : LiveData<ResultMovieEntity>
}