package com.anggit97.academy.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class BookmarkViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): LiveData<ArrayList<CourseEntity>>{
        return mAcademyRepository.getAllCourses()
    }
}