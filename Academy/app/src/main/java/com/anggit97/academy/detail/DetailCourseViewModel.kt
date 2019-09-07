package com.anggit97.academy.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository


/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class DetailCourseViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    private var courseEntity: CourseEntity? = null
    private var courseId = MutableLiveData<String>()

    var courseModules = Transformations.switchMap(
        courseId
    ) { mCourseId ->
        mAcademyRepository.getCourseWithModules(mCourseId)
    }

    fun setCourseId(courseId: String){
        this.courseId.value = courseId
    }

    fun getCourseId(): String?{
        return courseId.value
    }

    fun setBookmarks(){
        courseModules.value?.let {
            val courseModule = it.data
            courseModule?.let {
                val courseEntity = courseModule.courseEntity

                val newState = courseEntity?.bookmarked
                mAcademyRepository.setCourseBookmark(courseEntity!!, newState!!)
            }
        }
    }
}