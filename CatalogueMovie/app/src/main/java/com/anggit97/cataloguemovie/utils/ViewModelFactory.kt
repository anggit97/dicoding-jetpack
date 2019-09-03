package com.anggit97.cataloguemovie.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.ui.detail.DetailMovieViewModel
import com.anggit97.cataloguemovie.ui.home.movie.MovieViewModel
import com.anggit97.cataloguemovie.ui.home.tvshow.TvShowViewModel

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
class ViewModelFactory private constructor(private val mAcademyRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                mAcademyRepository
            ) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(
                mAcademyRepository
            ) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                mAcademyRepository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Injection.provideRepository(application)?.let {
                            ViewModelFactory(
                                it
                            )
                        }
                    }
                }
            }
            return INSTANCE
        }
    }
}