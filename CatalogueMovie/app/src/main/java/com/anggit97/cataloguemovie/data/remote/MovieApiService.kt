package com.anggit97.cataloguemovie.data.remote

import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Anggit Prayogo on 2019-09-05.
 * Github : @anggit97
 */
interface MovieApiService {

    @GET("movie/now_playing")
    fun getMovies(): Response<ArrayList<ResultMovieResponse>>

    @GET("tv/popular")
    fun getTvShows() : Response<ArrayList<ResultMovieResponse>>
}