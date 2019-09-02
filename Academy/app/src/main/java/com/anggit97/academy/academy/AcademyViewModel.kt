package com.anggit97.academy.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class AcademyViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    fun getCourses(): LiveData<ArrayList<CourseEntity>> {
        return mAcademyRepository.getAllCourses()
    }
}