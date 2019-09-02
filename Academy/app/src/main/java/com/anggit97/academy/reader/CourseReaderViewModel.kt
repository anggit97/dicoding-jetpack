package com.anggit97.academy.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository


/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    private var courseId: String? = null
    private var moduleId: String? = null

    val modules: LiveData<ArrayList<ModuleEntity>>
        get() = academyRepository.getAllModulesByCourse(courseId!!)

    val selectedModule: LiveData<ModuleEntity?>
        get() = academyRepository.getContent(courseId!!, moduleId!!)

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId = moduleId
    }
}