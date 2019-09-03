package com.anggit97.cataloguemovie.data.remote

import android.os.Handler
import com.anggit97.cataloguemovie.utils.JsonHelper

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
class RemoteMovieRepository constructor(
    private val jsonHelper: JsonHelper
) {

    private val SERVICE_LATENCY_IN_MILLIS : Long = 2000

    companion object {

        private var INSTANCE: RemoteMovieRepository? = null

        fun getInstance(helper: JsonHelper): RemoteMovieRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteMovieRepository(helper)
            }
            return INSTANCE as RemoteMovieRepository
        }
    }

    fun getAllMovies(callback: LoadMovieCallback){
        val handler =  Handler()
        handler.postDelayed({
            callback.onAllMovieRecieved(jsonHelper.loadMovies())
        },SERVICE_LATENCY_IN_MILLIS)
    }

    fun getAllTvShows(callback: LoadTvShowCallback){
        val handler = Handler()
        handler.postDelayed({
            callback.onAllTvShowRecieved(jsonHelper.loadTvShows())
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getDetailMovie(movieId: String, callback: LoadDetailMovieCallback){
        val handler = Handler()
        handler.postDelayed({
            callback.onDetailMovieRecieved(jsonHelper.loadDetailMovie(movieId))
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getDetailTvShow(tvShowId: String, callback: LoadDetailMovieCallback){
        val handler = Handler()
        handler.postDelayed({
            callback.onDetailMovieRecieved(jsonHelper.loadDetailTvShow(tvShowId))
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadMovieCallback{

        fun onAllMovieRecieved(movieResponses: ArrayList<ResultMovieResponse>)

        fun onDataNotAvailable()
    }

    interface LoadTvShowCallback{

        fun onAllTvShowRecieved(tvShowResponses: ArrayList<ResultMovieResponse>)

        fun onDataNotAvailable()
    }

    interface LoadDetailMovieCallback{

        fun onDetailMovieRecieved(detailMovie: ResultMovieResponse?)

        fun onDataNotAvailable()
    }
}