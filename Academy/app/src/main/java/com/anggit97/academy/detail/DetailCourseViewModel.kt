package com.anggit97.academy.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository


/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class DetailCourseViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    private var courseEntity: CourseEntity? = null
    private var courseId: String = ""

    fun getCourse(): LiveData<CourseEntity?> {
        return mAcademyRepository.getCourseWithModules(courseId)
    }

    fun getModules(): LiveData<ArrayList<ModuleEntity>> {
        return mAcademyRepository.getAllModulesByCourse(courseId)
    }

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun getCourseId(): String {
        return courseId
    }
}