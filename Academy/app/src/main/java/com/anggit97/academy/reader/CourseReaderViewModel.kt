package com.anggit97.academy.reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.data.source.local.entity.ModuleEntity


/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    private val courseId = MutableLiveData<String>()
    private val moduleId = MutableLiveData<String>()


    var modules = Transformations.switchMap(
        courseId
    ) { mCourseId ->
        academyRepository.getAllModulesByCourse(mCourseId)
    }

    fun setCourseId(courseId: String) {
        this.courseId.value = courseId
    }

    var selectedModule = Transformations.switchMap(
        moduleId
    ) { selectedPosition -> academyRepository.getContent(selectedPosition) }

    fun setSelectedModule(moduleId: String?) {
        this.moduleId.value = moduleId
    }

    fun readContent(module: ModuleEntity) {
        academyRepository.setReadModule(module)
    }

    fun getModuleSize(): Int {
        if (modules.value != null) {
            val moduleEntities = modules.value!!.data
            if (moduleEntities != null) {
                return moduleEntities.size
            }
        }
        return 0
    }

    fun setNextPage() {
        if (selectedModule.value != null && modules.value != null) {
            val moduleEntity = selectedModule.value!!.data
            val moduleEntities = modules.value!!.data
            if (moduleEntity != null && moduleEntities != null) {
                val position = moduleEntity.position!!
                if (position < moduleEntities.size && position >= 0) {
                    setSelectedModule(moduleEntities[position + 1].moduleId)
                }
            }
        }
    }

    fun setPrevPage() {
        if (selectedModule.value != null && modules.value != null) {
            val moduleEntity = selectedModule.value!!.data
            val moduleEntities = modules.value!!.data
            if (moduleEntity != null && moduleEntities != null) {
                val position = moduleEntity.position!!
                if (position < moduleEntities.size && position >= 0) {
                    setSelectedModule(moduleEntities.get(position - 1).moduleId)
                }
            }
        }
    }

}