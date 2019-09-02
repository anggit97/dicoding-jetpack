package com.anggit97.academy.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.anggit97.academy.di.Injection
import com.anggit97.academy.data.source.AcademyRepository
import androidx.lifecycle.ViewModelProvider
import com.anggit97.academy.reader.CourseReaderViewModel
import com.anggit97.academy.bookmark.BookmarkViewModel
import com.anggit97.academy.detail.DetailCourseViewModel
import com.anggit97.academy.academy.AcademyViewModel





/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
class ViewModelFactory private constructor(private val mAcademyRepository: AcademyRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AcademyViewModel::class.java) -> AcademyViewModel(mAcademyRepository) as T
            modelClass.isAssignableFrom(DetailCourseViewModel::class.java) -> DetailCourseViewModel(mAcademyRepository) as T
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(mAcademyRepository) as T
            modelClass.isAssignableFrom(CourseReaderViewModel::class.java) -> CourseReaderViewModel(mAcademyRepository) as T
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