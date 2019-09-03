package com.anggit97.cataloguemovie.utils

import android.app.Application
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.remote.RemoteMovieRepository

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
object Injection {

    fun provideRepository(application: Application): MovieRepository? {
        val remoteRepository = RemoteMovieRepository.getInstance(JsonHelper(application))
        return MovieRepository.getInstance(remoteRepository)
    }
}